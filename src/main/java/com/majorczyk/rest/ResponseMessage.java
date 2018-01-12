package com.majorczyk.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Piotr on 2018-01-12.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseMessage {

    String error_field;
    String error;
}
