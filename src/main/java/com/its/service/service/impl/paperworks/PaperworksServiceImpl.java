package com.its.service.service.impl.paperworks;

import com.its.service.entity.paperwork.Paperworks;
import com.its.service.enums.RecordStatus;
import com.its.service.helper.BasicAudit;
import com.its.service.repository.paperworks.PaperworksReporsitory;
import com.its.service.service.paperworks.PaperworksService;
import com.its.service.utils.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaperworksServiceImpl implements PaperworksService {

    private final PaperworksReporsitory repository;


    @Override
    public Paperworks save(Paperworks paperworks) {
        BasicAudit.setAttributeForCreateUpdate(paperworks, false, RecordStatus.ACTIVE);
        paperworks.setPaperworkTitle(generatePaperworkTitle(paperworks));
        return repository.save(paperworks);
    }

    private String generatePaperworkTitle(Paperworks paperworks) {
        return paperworks.getYear() + "-" + paperworks.getMonth() + "-" + "Paperwork";
    }

    @Override
    public Paperworks update(Paperworks paperworks) {
        return null;
    }

    @Override
    public Paperworks findById(Long id) {
        return null;
    }

    @Override
    public void delete(Paperworks paperworks) {

    }

    @Override
    public List<Paperworks> findAll() {
        return List.of();
    }

    @Override
    public PaginatedResponse list(String sort, String dir, int page, int size) {
        return null;
    }
}
