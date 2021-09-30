package org.comppress.comppressbackend.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@ToString
public class ApiResponse<T>{

    private final T body;
    private final String message;
    private final int code;

}
