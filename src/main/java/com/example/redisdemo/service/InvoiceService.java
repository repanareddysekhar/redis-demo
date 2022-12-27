package com.example.redisdemo.service;

import com.example.redisdemo.InvoiceNotFoundException;
import com.example.redisdemo.entity.Invoice;
import com.example.redisdemo.repository.InvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepo invoiceRepo;

    public Invoice save(Invoice invoice) {
        return invoiceRepo.save(invoice);
    }

    @CachePut(value = "invoice", key = "#id")
    public Invoice updateInvoice(Invoice invoice, Integer id) {
        Invoice invoice1 = invoiceRepo.findById(id).orElseThrow(() -> new InvoiceNotFoundException("Not found"));
        invoice1.setInvAmt(invoice.getInvAmt());
        invoice1.setInvName(invoice.getInvName());
        return invoiceRepo.save(invoice1);
    }

    @Cacheable(cacheNames = "invoice", key = "#pageable")
    public List<Invoice> getAllInvoice(Pageable pageable) {
        return invoiceRepo.findAll(pageable).toList();
    }

    @Cacheable(value = "invoice", key = "#id")
    public Invoice getOneInvoice(Integer id) {
        Invoice invoice = invoiceRepo.findById(id).orElseThrow(() -> new InvoiceNotFoundException("Not found"));
        return invoice;
    }

    @CacheEvict(value = "invoice", key = "#id")
    public void deleteInvoice(Integer id) {
        Invoice invoice = invoiceRepo.findById(id).orElseThrow(() -> new InvoiceNotFoundException("Not found"));
        invoiceRepo.delete(invoice);
    }
}
