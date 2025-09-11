package com.jpd.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jpd.model.PdfDocument;
import java.util.List;


@Repository
public interface PdfDocumentRepository extends CrudRepository<PdfDocument, Long> {
PdfDocument findByDocId(long docId);
}
