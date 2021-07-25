package com.example.annictUtil.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class UserStatus {
    private String title;
    private String myselfStatusState;

    //自身と検索対象のステータスが異なる場合のみ出力する (同一の場合、myselfStatusStateで自明なため)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String searchTargetStatusState;
}
