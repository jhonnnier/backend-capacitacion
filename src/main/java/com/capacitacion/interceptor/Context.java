package com.capacitacion.interceptor;

public class Context {

    private Context() {
    }

    private static final ThreadLocal<String> TENANT_CONTEXT = new ThreadLocal<>();

    private static final ThreadLocal<String> USER_ID_CONTEXT = new ThreadLocal<>();

    private static final ThreadLocal<String> ROLE_ID_CONTEXT = new ThreadLocal<>();

    private static final ThreadLocal<String> TIMEZONE_CONTEXT = new ThreadLocal<>();

    private static final ThreadLocal<String> CURRENCY_CONTEXT = new ThreadLocal<>();

    private static final ThreadLocal<String> LOCALE_CONTEXT = new ThreadLocal<>();

    private static final ThreadLocal<String> BRANCH_ID_CONTEXT = new ThreadLocal<>();

    private static final ThreadLocal<String> PROFESSIONAL_ID_CONTEXT = new ThreadLocal<>();

    private static final ThreadLocal<String> PROVIDER_ID_CONTEXT = new ThreadLocal<>();

    private static final ThreadLocal<String> PROFESSIONAL_BRANCH_REL_ID_CONTEXT = new ThreadLocal<>();

    private static final ThreadLocal<String> CSV_FORMAT_CONTEXT = new ThreadLocal<>();

    private static final ThreadLocal<String> CUSTOMER_TYPE_CONTEXT = new ThreadLocal<>();

    public static String getTenantId() {
        return TENANT_CONTEXT.get();
    }

    public static void setTenantId(final String tenantId) {
        TENANT_CONTEXT.set(tenantId);
    }

    public static void setUserId(final String tenantId) {
        USER_ID_CONTEXT.set(tenantId);
    }

    public static String getUserId() {
        return USER_ID_CONTEXT.get();
    }

    public static String getRoleId() {
        return ROLE_ID_CONTEXT.get();
    }

    public static void setRoleId(final String tenantId) {
        ROLE_ID_CONTEXT.set(tenantId);
    }

    public static String getTimezone() {
        return TIMEZONE_CONTEXT.get();
    }

    public static void setTimezone(final String timezone) {
        TIMEZONE_CONTEXT.set(timezone);
    }

    public static String getCurrency() {
        return CURRENCY_CONTEXT.get();
    }

    public static void setCurrency(final String currency) {
        CURRENCY_CONTEXT.set(currency);
    }

    public static String getLocale() {
        return LOCALE_CONTEXT.get();
    }

    public static void setLocale(final String locale) {
        LOCALE_CONTEXT.set(locale);
    }

    public static String getBranchId() {
        return BRANCH_ID_CONTEXT.get();
    }

    public static void setBranchId(final String branchId) {
        BRANCH_ID_CONTEXT.set(branchId);
    }

    public static String getProfessionalId() {
        return PROFESSIONAL_ID_CONTEXT.get();
    }

    public static void setProfessionalId(final String professionalId) {
        PROFESSIONAL_ID_CONTEXT.set(professionalId);
    }

    public static String getProviderId() {
        return PROVIDER_ID_CONTEXT.get();
    }

    public static void setProviderId(final String providerId) {
        PROVIDER_ID_CONTEXT.set(providerId);
    }

    public static String getCsvFormat() {
        return CSV_FORMAT_CONTEXT.get();
    }

    public static void setCsvFormat(final String csvFormat) {
        CSV_FORMAT_CONTEXT.set(csvFormat);
    }

    public static String getCustomerType() {
        return CUSTOMER_TYPE_CONTEXT.get();
    }

    public static void setCustomerType(final String customerType) {
        CUSTOMER_TYPE_CONTEXT.set(customerType);
    }

    public static String getProfessionalBranchRelId() {
        return PROFESSIONAL_BRANCH_REL_ID_CONTEXT.get();
    }

    public static void setProfessionalBranchRelId(final String professionalBranchRelId) {
        PROFESSIONAL_BRANCH_REL_ID_CONTEXT.set(professionalBranchRelId);
    }

    public static void clear() {
        TENANT_CONTEXT.remove();
        TIMEZONE_CONTEXT.remove();
        USER_ID_CONTEXT.remove();
        ROLE_ID_CONTEXT.remove();
        CURRENCY_CONTEXT.remove();
        LOCALE_CONTEXT.remove();
        BRANCH_ID_CONTEXT.remove();
        PROFESSIONAL_ID_CONTEXT.remove();
        PROVIDER_ID_CONTEXT.remove();
        CSV_FORMAT_CONTEXT.remove();
        CUSTOMER_TYPE_CONTEXT.remove();
        PROFESSIONAL_BRANCH_REL_ID_CONTEXT.remove();
        TIMEZONE_CONTEXT.remove();
    }
}
