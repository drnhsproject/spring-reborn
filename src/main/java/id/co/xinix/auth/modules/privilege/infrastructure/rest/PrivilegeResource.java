package id.co.xinix.auth.modules.privilege.infrastructure.rest;

import id.co.xinix.auth.modules.privilege.application.dto.PagedResult;
import id.co.xinix.auth.modules.privilege.application.dto.PrivilegeCommand;
import id.co.xinix.auth.modules.privilege.application.dto.PrivilegeCreatedResult;
import id.co.xinix.auth.modules.privilege.application.dto.QueryFilter;
import id.co.xinix.auth.modules.privilege.application.usecase.CreatePrivilege;
import id.co.xinix.auth.modules.privilege.application.usecase.GetList;
import id.co.xinix.auth.services.ListResponse;
import id.co.xinix.auth.services.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/privileges")
@Tag(name = "Privilege API", description = "Operation privilege")
@AllArgsConstructor
public class PrivilegeResource {

    private final CreatePrivilege createPrivilege;

    private final GetList getList;

    @Operation(summary = "Create User", description = "Create new privilege")
    @PostMapping("")
    public ResponseEntity<SingleResponse<PrivilegeCreatedResult>> createUser(
        @Valid @RequestBody PrivilegeCommand command
    ) throws URISyntaxException {
        PrivilegeCreatedResult result = createPrivilege.handle(command);
        SingleResponse<PrivilegeCreatedResult> response = new SingleResponse<>("privilege created", result);

        return ResponseEntity.created(new URI("/api/privileges/")).body(response);
    }

    @GetMapping("")
    public ResponseEntity<ListResponse<PagedResult>> getAllPrivilege(
        @RequestParam(value = "!search", required = false) String search,
        @ModelAttribute QueryFilter queryFilter,
        Pageable pageable
    ) {
        queryFilter.setSearch(search);
        Page<PagedResult> results = getList.handle(queryFilter, pageable);
        ListResponse<PagedResult> response = new ListResponse<>(
          "privilege retrieved",
          results.getContent(),
          results.getTotalElements()
        );

        return ResponseEntity.ok().body(response);
    }
}
