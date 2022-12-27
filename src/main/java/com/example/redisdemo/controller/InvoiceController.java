package com.example.redisdemo.controller;

import com.example.redisdemo.entity.Invoice;
import com.example.redisdemo.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/saveInvoice")
    public Invoice saveInvoice(@RequestBody Invoice invoice) {
        return invoiceService.save(invoice);
    }

    @GetMapping("/allInv")
    public ResponseEntity<List<Invoice>> getAllInvoices(){
        return ResponseEntity.ok(invoiceService.getAllInvoice(Pageable.unpaged()));
    }

    @GetMapping("/getOne/{id}")
    public Invoice getOneInvoice(@PathVariable Integer id) {
        return invoiceService.getOneInvoice(id);
    }

    @PutMapping("/modify/{id}")
    public Invoice updateInvoice(@RequestBody Invoice inv, @PathVariable Integer id) {
        return invoiceService.updateInvoice(inv, id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable Integer id) {
        invoiceService.deleteInvoice(id);
        return "Employee with id: "+id+ " Deleted !";
    }
}
