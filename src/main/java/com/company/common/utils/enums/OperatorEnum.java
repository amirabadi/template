package com.company.common.utils.enums;

public enum OperatorEnum {
    EQUAL,NOTEQUAL,ASCENDING,DESCENDING;
    public static final String[] OPERATORS = {"eq","neq", "asc", "desc"};

    public static OperatorEnum getOperator(String input) {
        switch (input) {
            case "eq":
                return EQUAL;
            case "neq":
                return NOTEQUAL;
            case "asc":
                return ASCENDING;
            case "desc":
                return DESCENDING;
            default:
                return null;
        }
    }
}
