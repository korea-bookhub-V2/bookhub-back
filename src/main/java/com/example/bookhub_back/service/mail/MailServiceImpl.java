package com.example.bookhub_back.service.mail;

import com.example.bookhub_back.common.constants.ResponseCode;
import com.example.bookhub_back.common.constants.ResponseMessageKorean;
import com.example.bookhub_back.dto.ResponseDto;
import com.example.bookhub_back.dto.auth.request.LoginIdFindSendEmailRequestDto;
import com.example.bookhub_back.dto.auth.request.PasswordFindSendEmailRequestDto;
import com.example.bookhub_back.dto.auth.request.PasswordResetRequestDto;
import com.example.bookhub_back.entity.Employee;
import com.example.bookhub_back.provider.JwtTokenProvider;
import com.example.bookhub_back.repository.BranchRepository;
import com.example.bookhub_back.repository.EmployeeRepository;
import com.example.bookhub_back.repository.EmployeeSignUpApprovalRepository;
import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmployeeSignUpApprovalRepository employeeSignUpApprovalRepository;
    private final BranchRepository branchRepository;

    public void sendLoginIdEmailMessage(String email, String token) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("[BookHub] 이메일 인증 요청");

        String htmlContent = """
                <h2>[이메일 인증 요청]</h2>
                <p>안녕하세요,</P>
                <br />
                <p>아이디 찾기 이메일 인증을 위해 아래 버튼을 클릭해 주세요.</p>
                <a href="http://localhost:5173/auth/login-id-find?token=%s">이메일 인증하기</a>
                <p>본 이메일은 인증 목적으로 발송되었습니다. 인증을 원하지 않으시면 무시하셔도 됩니다.</p>
            """.formatted(token);

        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

    @Override
    public Mono<ResponseEntity<ResponseDto<String>>> sendEmailFindId(LoginIdFindSendEmailRequestDto dto) {
        return Mono.fromCallable(() -> {
            Employee employee = employeeRepository.findByEmail(dto.getEmail())
                .orElse(null);

            if (employee == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.<String>fail(ResponseCode.NO_EXIST_USER_EMAIL, ResponseMessageKorean.NO_EXIST_USER_EMAIL));
            }

            if (!employee.getPhoneNumber().equals(dto.getPhoneNumber())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.<String>fail(ResponseCode.NOT_MATCH_USER_TEL, ResponseMessageKorean.NOT_MATCH_USER_TEL));
            }

            String token = jwtTokenProvider.generateEmailToken(employee.getLoginId(), employee.getEmail());
            sendLoginIdEmailMessage(dto.getEmail(), token);

            return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>success(ResponseCode.SUCCESS, "이메일 전송 성공"));

        }).subscribeOn(Schedulers.boundedElastic());

    }

    @Override
    public Mono<ResponseEntity<ResponseDto<String>>> verifyEmailId(String token) {
        return Mono.fromCallable(() -> {
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.<String>fail(ResponseCode.INVALID_TOKEN, ResponseMessageKorean.INVALID_TOKEN));
            }

            Claims claims = jwtTokenProvider.getClaims(token);
            String email = claims.get("email", String.class);
            String loginId = jwtTokenProvider.getLoginId(token);

            if (email == null || loginId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body((ResponseDto.<String>fail(ResponseCode.INVALID_TOKEN, ResponseMessageKorean.INVALID_TOKEN)));
            }

            Employee employee = employeeRepository.findByEmail(email)
                .orElse(null);

            if (employee == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.<String>fail(ResponseCode.NO_EXIST_USER_EMAIL, ResponseMessageKorean.NO_EXIST_USER_EMAIL));
            }

            if (!employee.getLoginId().equals(loginId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.<String>fail(ResponseCode.NOT_MATCH_USER_INFO, ResponseMessageKorean.NOT_MATCH_USER_INFO));
            }

            return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, employee.getLoginId()));

        }).subscribeOn(Schedulers.boundedElastic());
    }

    public void sendPasswordChangeEmailMessage(String email, String token) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("[BookHub] 이메일 인증 요청");

        String htmlContent = """
                <h2>[이메일 인증 요청]</h2>
                <p>안녕하세요,</P>
                <br />
                <p>비밀번호 변경 이메일 인증을 위해 아래 버튼을 클릭해 주세요.</p>
                <a href="http://localhost:5173/auth/password-change?token=%s">이메일 인증하기</a>
                <p>본 이메일은 인증 목적으로 발송되었습니다. 인증을 원하지 않으시면 무시하셔도 됩니다.</p>
            """.formatted(token);

        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

    @Override
    public Mono<ResponseEntity<ResponseDto<String>>> sendEmailResetPassword(PasswordFindSendEmailRequestDto dto) {
        return Mono.fromCallable(() -> {
            Employee employee = employeeRepository.findByLoginId(dto.getLoginId())
                .orElse(null);

            if (employee == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.<String>fail(ResponseCode.NO_EXIST_USER_ID, ResponseMessageKorean.NO_EXIST_USER_ID));
            }

            if (!employee.getEmail().equals(dto.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.<String>fail(ResponseCode.NOT_MATCH_USER_EMAIL, ResponseMessageKorean.NO_EXIST_USER_EMAIL));
            }

            if (!employee.getPhoneNumber().equals(dto.getPhoneNumber())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.<String>fail(ResponseCode.NOT_MATCH_USER_TEL, ResponseMessageKorean.NOT_MATCH_USER_TEL));
            }

            String token = jwtTokenProvider.generateEmailToken(employee.getLoginId(), employee.getEmail());
            sendPasswordChangeEmailMessage(employee.getEmail(), token);

            return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>success(ResponseCode.SUCCESS, "이메일 전송 성공"));
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<ResponseEntity<ResponseDto<String>>> verifyLoginIdPassword(String token) {
        return Mono.fromCallable(() -> {
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.<String>fail(ResponseCode.INVALID_TOKEN, ResponseMessageKorean.INVALID_TOKEN));
            }

            Claims claims = jwtTokenProvider.getClaims(token);
            String email = claims.get("email", String.class);
            String loginId = jwtTokenProvider.getLoginId(token);

            if (email == null || loginId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body((ResponseDto.<String>fail(ResponseCode.INVALID_TOKEN, ResponseMessageKorean.INVALID_TOKEN)));
            }

            Employee employee = employeeRepository.findByLoginId(loginId)
                .orElse(null);

            if (employee == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.<String>fail(ResponseCode.NO_EXIST_USER_ID, ResponseMessageKorean.NO_EXIST_USER_ID));
            }

            if (!employee.getEmail().equals(email)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.<String>fail(ResponseCode.NOT_MATCH_USER_INFO, ResponseMessageKorean.NOT_MATCH_USER_INFO));
            }

            return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, employee.getLoginId()));

        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<ResponseEntity<ResponseDto<String>>> passwordChange(String token, PasswordResetRequestDto dto) {
        return Mono.fromCallable(() -> {
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.<String>fail(ResponseCode.INVALID_TOKEN, ResponseMessageKorean.INVALID_TOKEN));
            }

            Claims claims = jwtTokenProvider.getClaims(token);
            String email = claims.get("email", String.class);
            String loginId = jwtTokenProvider.getLoginId(token);

            if (email == null || loginId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body((ResponseDto.<String>fail(ResponseCode.INVALID_TOKEN, ResponseMessageKorean.INVALID_TOKEN)));
            }

            Employee employee = employeeRepository.findByLoginId(loginId)
                .orElse(null);

            if (employee == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.<String>fail(ResponseCode.NO_EXIST_USER_ID, ResponseMessageKorean.NO_EXIST_USER_ID));
            }

            if (!employee.getEmail().equals(email)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.<String>fail(ResponseCode.NOT_MATCH_USER_INFO, ResponseMessageKorean.NOT_MATCH_USER_INFO));
            }

            String password = dto.getPassword();
            String confirmPassword = dto.getConfirmPassword();

            if (!password.equals(confirmPassword)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.fail(ResponseCode.NOT_MATCH_PASSWORD, ResponseMessageKorean.NOT_MATCH_PASSWORD));
            }

            String encodePassword = bCryptPasswordEncoder.encode(password);
            employee.setPassword(encodePassword);
            employeeRepository.save(employee);

            return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, "비밀번호가 변경되었습니다."));
        });
    }
}
