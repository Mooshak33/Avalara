package com.avalara.avalara.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputDataFormat {
    private String format;
    private List<String> versions;
}
