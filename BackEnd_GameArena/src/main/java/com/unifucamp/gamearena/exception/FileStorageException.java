package com.unifucamp.gamearena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class FileStorageException extends GameArenaException {

  private String detailMessage;

  public FileStorageException(String detailMessage) {
    this.detailMessage = detailMessage;
  }

  @Override
  public ProblemDetail toProblemDetail() {
    var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

    pb.setDetail(detailMessage);

    return pb;
  }
}
