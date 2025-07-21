package com.unifucamp.gamearena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class AuthenticationException extends GameArenaException {

    private String detailMessage;

    public AuthenticationException(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);

        pb.setTitle("Acesso não autorizado");
        pb.setDetail(detailMessage);

        return pb;
    }
}
