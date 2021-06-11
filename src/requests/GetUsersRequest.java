package requests;

import enums.RoleEnum;

public class GetUsersRequest {
    private RoleEnum role;
    private String search;

    public RoleEnum getRole() {
        return role;
    }

    public String getSearch() {
        return search;
    }
}
