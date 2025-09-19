package com.unifucamp.gamearena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class ArmazenamentoException extends GameArenaException {

  private String detailMessage;

  public ArmazenamentoException(String detailMessage) {
    this.detailMessage = detailMessage;
  }

  @Override
  public ProblemDetail toProblemDetail() {
    var pb = ProblemDetail.forStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE);

    pb.setDetail(detailMessage);

    return pb;
  }
}
