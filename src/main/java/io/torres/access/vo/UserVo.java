package io.torres.access.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserVo {

    //rivate Long id;
    private String email;
    private String password;
    //private List<String> roles;
}
