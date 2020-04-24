package com.mimi.mlibrary.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mimi.mlibrary.exceptions.ResourceNotFoundException;
import com.mimi.mlibrary.mapper.borrowing.BorrowingMapper;
import com.mimi.mlibrary.model.dto.borrowing.BorrowingDto;
import com.mimi.mlibrary.model.entity.borrowing.BorrowingJson;
import com.mimi.mlibrary.service.contract.BorrowingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Borrowings")
public class BorrowingController {

    private BorrowingService borrowingService;

    public BorrowingController( BorrowingService borrowingService ) {
        this.borrowingService = borrowingService;
    }

    @GetMapping
    public List<BorrowingDto> getAllBorrowings() {
        return borrowingService.findAll();
    }

    @GetMapping( "/{id}" )
    public BorrowingDto findBorrowingById( @PathVariable int id ) {
        return borrowingService.findBorrowingById( id );
    }


    @GetMapping( "/Members/{id}" )
    public List<BorrowingDto> findAllByMemberId( @PathVariable int id ) {
        return borrowingService.findByMemberId( id );
    }

    @PostMapping( consumes={"application/json"})
    public ResponseEntity<Void> addBorrowing(@RequestBody BorrowingJson borrowingJson ) {

        BorrowingDto addedBorrowing = borrowingService.save( borrowingJson.getMember(), borrowingJson.getCopy() );

        if( addedBorrowing == null ) throw new ResourceNotFoundException( "L'utilisateur ne peut pas emprunter cette exemplaire.\n Exemplaire indisponible ou l'utilisateur a déjà 5 emprunts en cours." );

        /*if( addedBorrowing == null)
            return ResponseEntity.noContent().build();*/

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand( addedBorrowing.getId() ).toUri();
        return ResponseEntity.created( location ).build();
    }

    @PutMapping( "/return/{borrowingId}" )
    public void extendBorrowingReturnDate( @PathVariable int borrowingId ) {
        borrowingService.extendBorrowingReturnDateById( borrowingId );
    }

    @PutMapping( "/status/{id}" )
    public void updateBorrowingStatus( @PathVariable int id ) {
        borrowingService.updateBorrowingStatus( id );
    }

    @GetMapping( "/delay" )
    public List<BorrowingDto> findByDelay() {
       return borrowingService.findByDelay();
    }

    @GetMapping( "/delay/email" )
    public Map<String, LocalDate> getOutdatedBorrowingsAndEmailMember() {
        return  borrowingService.findOutdatedBorrowingsEmailMember();
    }


}

