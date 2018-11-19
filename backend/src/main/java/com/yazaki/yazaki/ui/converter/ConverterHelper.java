package com.yazaki.yazaki.ui.converter;

public class ConverterHelper {

    public static String getOperation(Byte revisionType) {
        if (revisionType == 0) {
            return "Добавен";
        } else if (revisionType == 1) {
            return "Редактиран";
        } else {
            return "Изтрит";
        }
    }

    public static String getRole(Integer authorityId) {
        if (authorityId == 1) {
            return "Админ";
        } else if (authorityId == 2) {
            return "Клиент";
        } else {
            return "Служител";
        }
    }
}