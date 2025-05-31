package com.its.service.service.impl;

import com.its.service.constant.DefaultConstant;
import com.its.service.constant.MessageConstant;
import com.its.service.entity.ShopBranch;
import com.its.service.exception.AlreadyExistsException;
import com.its.service.repository.ShopBranchRepository;
import com.its.service.service.ShopBranchService;
import com.its.service.utils.PaginatedResponse;
import com.its.service.utils.PaginationUtils;
import com.its.service.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ShopBranchServiceImpl implements ShopBranchService {

    private final ShopBranchRepository repository;

    @Override
    public PaginatedResponse listAndSearch(String sort, String dir, int page, int size, String shopBranchName) {
        shopBranchName = shopBranchName.equals("") ? null : shopBranchName;
        sort = sort.isEmpty() ? "branchName" : sort;
        List<ShopBranch> data;
        Page<ShopBranch> pageData;

        if (StringUtils.isEmpty(shopBranchName)) {
            pageData = repository.findAll(PaginationUtils.getPageable(sort, dir, page, size));

        } else {
            pageData = repository.findByBranchNameContainingIgnoreCase(shopBranchName
                    , PaginationUtils.getPageable(sort, dir, page, size));
        }
        data = pageData.getContent().stream().toList();
        return PaginationUtils.getPaginatedResponse(pageData, data);
    }

    @Override
    public ShopBranch save(ShopBranch shopBranch) {
        return repository.save(shopBranch);
    }

    @Override
    public ShopBranch update(ShopBranch shopBranch) {
        if (Objects.isNull(shopBranch)) {
            throw new AlreadyExistsException(MessageConstant.DATA_NOT_PROVIDED);
        }
        if (Objects.isNull(shopBranch.getId()) || shopBranch.getId().equals(DefaultConstant.DEFAULT_VALUE_ZERO_LONG)) {
            throw new AlreadyExistsException(MessageConstant.PRIMARY_ID_NOT_PROVIDED);
        }
        try {
            shopBranch = save(shopBranch);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("Please provide unique data.The info you've provide are already exists!");
        }
        return shopBranch;
    }

    @Override
    public ShopBranch findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new AlreadyExistsException(MessageConstant.NOT_FOUND));
    }

    @Override
    public void delete(ShopBranch shopBranch) {
        if (Objects.isNull(shopBranch)) {
            throw new AlreadyExistsException(MessageConstant.DATA_NOT_PROVIDED);
        }
        if (Objects.isNull(shopBranch.getId()) || shopBranch.getId().equals(DefaultConstant.DEFAULT_VALUE_ZERO_LONG)) {
            throw new AlreadyExistsException(MessageConstant.PRIMARY_ID_NOT_PROVIDED);
        }
        try {
            repository.delete(shopBranch);
        } catch (Exception e) {
            throw new AlreadyExistsException("Failed to deleted the data!");
        }
    }

    @Override
    public List<ShopBranch> findAll() {
        return List.of();
    }

    @Override
    public PaginatedResponse list(String sort, String dir, int page, int size) {
        return null;
    }
}
