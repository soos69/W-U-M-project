package com.hnpl.wum.require.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequireSearchDto {

    private String searchBy;

    private String searchText;
}
