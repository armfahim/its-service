package com.its.service.service.impl.paperworks;

import com.its.service.constant.DefaultConstant;
import com.its.service.constant.MessageConstant;
import com.its.service.dto.paperworks.PaperworkBreakdownDto;
import com.its.service.entity.paperwork.PaperworkBreakdown;
import com.its.service.entity.paperwork.Paperworks;
import com.its.service.enums.RecordStatus;
import com.its.service.exception.AlreadyExistsException;
import com.its.service.helper.BasicAudit;
import com.its.service.repository.paperworks.CashPurchaseRepository;
import com.its.service.repository.paperworks.PaperworkBreakdownRepository;
import com.its.service.repository.paperworks.PaperworksRepository;
import com.its.service.service.paperworks.PaperworkBreakdownService;
import com.its.service.utils.PaginatedResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaperworkBreakdownServiceImpl implements PaperworkBreakdownService {

    private final PaperworkBreakdownRepository repository;
    private final PaperworksRepository paperworksRepository;
    private final CashPurchaseRepository cashPurchaseRepository;

    @Override
    @Transactional
    public PaperworkBreakdown save(PaperworkBreakdownDto dto) {
        PaperworkBreakdown paperworkBreakdown = new PaperworkBreakdown();
        dto.to(paperworkBreakdown);
        BasicAudit.setAttributeForCreateUpdate(paperworkBreakdown, false, RecordStatus.ACTIVE);

        Optional<Paperworks> paperworksOptional = paperworksRepository.findById(dto.getPaperworksId());
        paperworksOptional.ifPresent(paperworkBreakdown::setPaperworks);
        try {
            return repository.save(paperworkBreakdown);
        } catch (DataIntegrityViolationException e) {
            log.error("Failed to save the breakdown data", e);
            throw new AlreadyExistsException("Data for this date already exists");
        }
    }

    @Override
    public Boolean existsByPaperworkBreakdownDate(LocalDate paperworkBreakdownDate) {
        Optional<PaperworkBreakdown> optionalPaperworkBreakdown = repository.findByPaperworkDate(paperworkBreakdownDate);
        return optionalPaperworkBreakdown.isPresent() ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public PaperworkBreakdown findPaperworkBreakdownByDateAndId(Long paperworksId, LocalDate paperworkBreakdownDate) {
        Paperworks paperworks = paperworksRepository.findById(paperworksId).orElseThrow(() -> new AlreadyExistsException("Paperworks hasn't been found"));
        Optional<PaperworkBreakdown> optionalPaperworkBreakdown = repository.findByPaperworksAndPaperworkDate(paperworks, paperworkBreakdownDate);
        return optionalPaperworkBreakdown.orElse(null);
    }

    @Override
    public List<PaperworkBreakdown> findAllPaperworkBreakdownByPaperworkId(Long paperworksId) {
        Optional<List<PaperworkBreakdown>> optionalList = repository.findByPaperworksId(paperworksId);
        return optionalList.orElse(null);
    }

    @Override
    @Transactional
    public PaperworkBreakdown save(PaperworkBreakdown paperworkBreakdown) {
        return repository.save(paperworkBreakdown);
    }

    @Override
    public PaperworkBreakdown update(PaperworkBreakdown paperworkBreakdown) {
        return null;
    }

    @Override
    @Transactional
    public PaperworkBreakdown update(PaperworkBreakdownDto dto) {
        PaperworkBreakdown paperworkBreakdown = this.findById(dto.getId());
        cashPurchaseRepository.deleteByPaperworkBreakdown(paperworkBreakdown.getId());
        dto.to(paperworkBreakdown);

        if (Objects.isNull(paperworkBreakdown.getId()) || paperworkBreakdown.getId().equals(DefaultConstant.DEFAULT_VALUE_ZERO_LONG)) {
            throw new AlreadyExistsException(MessageConstant.PRIMARY_ID_NOT_PROVIDED);
        }
        try {
            paperworkBreakdown = save(paperworkBreakdown);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("Internal Server Error");
        }
        return paperworkBreakdown;
    }

    @Override
    public PaperworkBreakdown findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new AlreadyExistsException(MessageConstant.NOT_FOUND));
    }

    @Override
    public void delete(PaperworkBreakdown paperworkBreakdown) {

    }

    @Override
    public List<PaperworkBreakdown> findAll() {
        return List.of();
    }

    @Override
    public PaginatedResponse list(String sort, String dir, int page, int size) {
        return null;
    }
}
