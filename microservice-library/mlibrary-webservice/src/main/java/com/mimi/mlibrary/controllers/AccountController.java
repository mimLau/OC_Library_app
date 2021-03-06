package com.mimi.mlibrary.controllers;

import com.mimi.mlibrary.exceptions.ResourceNotFoundException;
import com.mimi.mlibrary.model.dto.account.EmployeeDto;
import com.mimi.mlibrary.model.dto.account.MemberDto;
import com.mimi.mlibrary.service.contract.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
public class AccountController {

    private AccountService accountService;

    AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping( "/Members" )
    public List<MemberDto> getAllMembers() {
        return accountService.findAll();
    }

    @GetMapping( "/Members/{id}" )
    public MemberDto getMemberById( @PathVariable("id") int id ) {
        return accountService.findById(id);
    }

    @GetMapping( value = "/Members", params = {"username"} )
    public MemberDto getMemberUsername( @RequestParam("username") String username ) {
        MemberDto memberDto = accountService.findMemberByUsername( username );
        if( memberDto == null ) throw new ResourceNotFoundException( "L'utilisateur recherché n'existe pas." );

        return memberDto;
    }

    @GetMapping( value = "/Members/delay" )
    public List<MemberDto> findMembersByOutdatedLoan() {
        return accountService.getMembersByOutdatedLoan();
    }

    @PostMapping( value = "/Members" )
    public ResponseEntity<Void> addMember( @RequestBody MemberDto memberDto ) {
        MemberDto addedMember = accountService.save( memberDto );
        if( addedMember == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand( addedMember.getId() ).toUri();
        return ResponseEntity.created( location ).build();
    }

    @PutMapping( value = "/Members/{id}" )
    public void incrementLoansNbLoans ( @PathVariable("id") int id ) {
        accountService.updateNbOfCurrentsLoans( id );
    }


    @GetMapping( value = "/Employees", params = {"username"}  )
    public EmployeeDto getEmployeeUsername( @RequestParam("username") String username ) {
        EmployeeDto employeeDto = accountService.findEmployeeByUsername( username );
        if( employeeDto == null ) throw new ResourceNotFoundException( "Il n'existe pas d'emlpoyé avec cet identifiant dans notre base de données." );

        return employeeDto;
    }


}

