package com.unifucamp.gamearena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class GameArenaException extends RuntimeException {

    public ProblemDetail toProblemDetail(){

        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pb.setTitle("Game Arena internal server error");

        return pb;
    }

}
