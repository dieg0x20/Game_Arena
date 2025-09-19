package com.unifucamp.gamearena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class UserDataAlreadyExistsException extends GameArenaException {

    private String detailMessage;

    public UserDataAlreadyExistsException(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("User already exists");
        pb.setDetail(detailMessage);

        return pb;
    }
}
