package com.unifucamp.gamearena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class UserNotExistsException extends GameArenaException {

    private String detailMessage;

    public UserNotExistsException(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        if (detailMessage.isBlank()) {
            detailMessage = "Não foi encontrado o registro na base de dados";
        }

        pb.setTitle("Usuário não encontrado");
        pb.setDetail(detailMessage);

        return pb;
    }
}
