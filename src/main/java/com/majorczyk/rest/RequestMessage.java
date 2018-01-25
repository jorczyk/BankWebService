package com.majorczyk.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Piotr on 2018-01-12.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestMessage {

    String source;
    long amount;
    String title;
    String name;
}
