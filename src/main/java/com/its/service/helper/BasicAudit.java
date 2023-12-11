package com.its.service.helper;

import com.its.service.entity.BaseEntity;
import com.its.service.enums.RecordStatus;

import java.util.UUID;

public abstract class BasicAudit {

    /**
     * TODO: set updatedBy and createdBy when Login module is full done
     * @param object
     * @param isUpdate
     * @param status
     * @return
     */
    public static Object setAttributeForCreateUpdate(Object object, boolean isUpdate, RecordStatus status) {
        ((BaseEntity) object).setRecordStatus(status);
        if (isUpdate) {
//			((BaseEntity) object).setUpdatedBy(UserUtil.getLoginUser());
        } else {
            //			((BaseEntity) object).setCreatedBy(UserUtil.getLoginUser());
        }
        return object;
    }
}
