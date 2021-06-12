package requests;

import enums.RoleEnum;

public class GetUsersRequest {
    private RoleEnum role;

    public GetUsersRequest(RoleEnum role, String search) {
        this.role = role;
        this.search = search;
    }

    public GetUsersRequest() {
    }

    private String search;

    public RoleEnum getRole() {
        return role;
    }

    public String getSearch() {
        return search;
    }
}
