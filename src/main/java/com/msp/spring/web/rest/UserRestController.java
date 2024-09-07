package com.msp.spring.web.rest;

import com.msp.spring.database.dto.PageResponse;
import com.msp.spring.database.dto.UserCreateEditDto;
import com.msp.spring.database.dto.UserFilter;
import com.msp.spring.database.dto.UserReadDto;
import com.msp.spring.exception.MyValidationException;
import com.msp.spring.service.CompanyService;
import com.msp.spring.service.FileService;
import com.msp.spring.service.UserService;
import com.msp.spring.validator.group.CreationAction;
import com.msp.spring.validator.group.FirstOrder;
import com.msp.spring.validator.group.SecondOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.*;
import javax.validation.groups.Default;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserRestController {

    private final UserService userService;
    private final CompanyService companyService;
    //бин с именем validator также есть в контексте. validator2 создается в конфигурации.
    private final Validator validator2;
    private final FileService fileService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponse<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
        Page<UserReadDto> page = userService.findAll(filter, pageable);
        return PageResponse.of(page);
    }

    @GetMapping("/{id}")
    public UserReadDto findById(@PathVariable("id") Long id) {
        return userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

/*
    @GetMapping(value = "/{id}/avatar", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] findAvatar(@PathVariable("id") Long id) {
        return userService.findAvatar(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
*/

    @GetMapping(value = "/{id}/avatar", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> findAvatar(@PathVariable("id") Long id) {
        return userService.findAvatar(id)
                .map(bytes -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .contentLength(bytes.length)
                        .body(bytes)
                )
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto create(@RequestBody /*@Validated({Default.class, CreationAction.class })*/ UserCreateEditDto user,
            /*BindingResult bindingResult*/Errors errors) {
        // в этом случае не работает Autowired. Видимо factory не связан с текущим контекстом
        //ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        //Validator validator = factory.getValidator();

        Set<ConstraintViolation<UserCreateEditDto>> violations = validator2.validate(user, Default.class, CreationAction.class);

        //if (errors.hasErrors() || true) {
        if (!violations.isEmpty()) {
            // этот механизм реализован в ResponseEntityExceptionHandler.handleMethodArgumentNotValid
            throw new MyValidationException(errors);
        }

        return userService.create(user);
    }

    @PostMapping(value = "/multi", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object create(@RequestPart(value = "model") UserCreateEditDto user, Errors errors, @RequestPart(value = "files", required = false) MultipartFile[] files) {

        Set<ConstraintViolation<UserCreateEditDto>> violations = validator2.validate(user, FirstOrder.class, SecondOrder.class, CreationAction.class, Default.class);
        if (!violations.isEmpty()) {
            buildErrors(violations, errors);
            throw new MyValidationException(errors);
        }

        Arrays.stream(files)
                .forEach(file -> {
                    try {
                        fileService.upload(file.getOriginalFilename(), file.getInputStream());
                    } catch (IOException e) {
                        log.error("error save file {}", file.getOriginalFilename(), e);
                    }
                });

        return userService.create(user);
    }

    @PutMapping("/{id}")
    public UserReadDto update(@PathVariable("id") Long id, @RequestBody /*@Validated({Default.class, UpdateAction.class })*/ @Valid UserCreateEditDto user) {
        return userService.update(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /*
        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void delete(@PathVariable("id") Long id) {
            if (!userService.delete(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return userService.delete(id)
               ? ResponseEntity.noContent().build()
               : ResponseEntity.notFound().build();
    }

    private void buildErrors(Set<ConstraintViolation<UserCreateEditDto>> violations, Errors errors) {
        violations.forEach(violation -> {
                    String fieldName = Objects.toString(violation.getPropertyPath(), null);
                    if (!StringUtils.hasText(fieldName)) {
                        fieldName = Objects.toString(violation.getConstraintDescriptor().getAttributes().get("field"), null);
                    }

                    if (StringUtils.hasText(fieldName)) {
                        errors.rejectValue(fieldName, violation.getMessageTemplate(), violation.getMessage());
                    } else {
                        errors.reject(violation.getMessageTemplate(), violation.getMessage());
                    }
                });
    }

}