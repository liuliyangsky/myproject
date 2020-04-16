package com.cherrypicks.myproject.jedis;

/**
 * 所有操作Redis的key必须从此获取
 */
public final class RedisKey {

    public static final String ENTITY_PREFIX = "Entity";

    public static final String STRING_PREFIX = "String";

    public static final String HASH_PREFIX = "Hash";

    public static final String SET_PREFIX = "Set";

    public static final String LIST_PREFIX = "List";

    public static final String DELIMITER = ":";

    private RedisKey() {

    }

    public static String getUserKey(final Long merchantId,final Long userId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("Merchant").append(DELIMITER).append(merchantId)
                .append("User").append(DELIMITER).append(userId)
                .append(DELIMITER).toString();
    }

    public static String getKeeperUserKey(final Long merchantId,final Long userId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("Merchant").append(DELIMITER).append(merchantId)
                .append("KeeperUser").append(DELIMITER).append(userId)
                .append(DELIMITER).toString();
    }

    public static String getConnectStringKey(final String str) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("Connect").append(DELIMITER).append(str)
                .append(DELIMITER).toString();
    }

    public static String getShareIdKey(final Long id) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("Share").append(DELIMITER).append(id)
                .append(DELIMITER).toString();
    }

    public static String getHomePageKey(final Long merchantId,final String langCode){
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("Merchant").append(DELIMITER).append(merchantId)
                .append(DELIMITER).append("Lang").append(DELIMITER).append(langCode).toString();
   }

    public static String getSendVerifyEmailCountKey(final Long merchantId,final Long tempEmailUserId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("SendVerifyEmail").append(DELIMITER)
                .append("Merchant").append(DELIMITER).append(merchantId)
                .append("TempEmailUserId").append(DELIMITER).append(tempEmailUserId)
                .append(DELIMITER).toString();
    }

    public static String getReSendSmsCountKey(final Long merchantId,final Long SmsVerifyId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("ReSendSms").append(DELIMITER)
                .append("Merchant").append(DELIMITER).append(merchantId)
                .append("SmsVerifyId").append(DELIMITER).append(SmsVerifyId)
                .append(DELIMITER).toString();
    }

    public static String getResetRegistrationEamilCountKey(final Long merchantId,final Long userId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("ResetRegistrationEamil").append(DELIMITER)
                .append("Merchant").append(DELIMITER).append(merchantId)
                .append("User").append(DELIMITER).append(userId)
                .append(DELIMITER).toString();
    }



    public static String getSendForgetPasswordEmailCountKey(final Long merchantId,final Long tempEmailUserId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("SendForgetPasswordEmail").append(DELIMITER)
                .append("Merchant").append(DELIMITER).append(merchantId)
                .append("TempEmailUserId").append(DELIMITER).append(tempEmailUserId)
                .append(DELIMITER).toString();
    }

    public static String getChangeUserPasswordCountKey(final Long merchantId,final Long userId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("ChangeUserPassword").append(DELIMITER)
                .append("Merchant").append(DELIMITER).append(merchantId)
                .append("User").append(DELIMITER).append(userId)
                .append(DELIMITER).toString();
    }

    public static String getMerchantConfigKey(final Long merchantId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("Merchant").append(DELIMITER).append(merchantId).append(DELIMITER)
                .toString();
    }

    public static String getSendSmsTimeKey(final Long merchantId,final Long userId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("SendSmsTime").append(DELIMITER)
                .append("Merchant").append(DELIMITER).append(merchantId)
                .append("User").append(DELIMITER).append(userId)
                .append(DELIMITER).toString();
    }

    public static String getMajorNoKey(final String projectCode,final String majorNo) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("projectCode").append(DELIMITER).append(projectCode).append(DELIMITER)
        		.append(DELIMITER).append("majorNo").append(DELIMITER).append(majorNo)
                .append(DELIMITER).toString();
    }

    public static String getMajorNoListKey(final String projectCode) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("projectCode").append(DELIMITER).append(projectCode).append(DELIMITER)
        		.append(DELIMITER).append("majorNoList")
                .append(DELIMITER).toString();
    }

    public static String getUserReceiveMsgCount(final Long userId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("userId").append(DELIMITER).append(userId).append(DELIMITER)
        		.append(DELIMITER).append("userReceiveMsgCount").append(DELIMITER).toString();
    }

    public static String getSubZoneBeaconsListKey(final String projectCode) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("projectCode").append(DELIMITER).append(projectCode).append(DELIMITER)
        		.append(DELIMITER).append("subZoneBeaconsList")
                .append(DELIMITER).toString();
    }

    public static String getSubZoneBeaconsKey(final String projectCode,final Long subzoneId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("projectCode").append(DELIMITER).append(projectCode).append(DELIMITER)
        		.append(DELIMITER).append("subzoneId").append(DELIMITER).append(subzoneId)
                .append(DELIMITER).toString();
    }

    public static String getUserBookmarkStoreKey(final Long userId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER)
                .append("User").append(DELIMITER).append(userId).append(DELIMITER).append("BookmarkStore")
                .append(DELIMITER).toString();
    }

    public static String getUserPreferenceCategoryKey(final Long userId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER)
                .append("User").append(DELIMITER).append(userId).append(DELIMITER).append("PreferenceCategory")
                .append(DELIMITER).toString();
    }

    public static String getWechatTokenKey(final Long merchantId,final Integer type) {
		return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("AccessToken").append(DELIMITER)
				.append("Merchant").append(DELIMITER).append(merchantId).append(DELIMITER)
				.append("WechatKeyType").append(DELIMITER).append(type)
				.append(DELIMITER).toString();
	}

    public static String getEventUserPalyCountKey(final Long platformId,final Long userId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("EventUserPalyCount").append(DELIMITER)
        		.append("platformId").append(DELIMITER).append(platformId).append("userId").append(DELIMITER).append(userId)
                .append(DELIMITER).toString();
    }

    public static String getEventUserMaxPalyCountKey(final Long platformId,final Long userId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("EventUserPalyMaxCount").append(DELIMITER)
        		.append("platformId").append(DELIMITER).append(platformId).append("userId").append(DELIMITER).append(userId)
                .append(DELIMITER).toString();
    }

    public static String getUserTokenKey(final Long userId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("UserToken").append(DELIMITER)
        		.append("userId").append(DELIMITER).append(userId)
                .append(DELIMITER).toString();
    }

    public static String getEventNoLimitCouponMapKey(final Long eventId, final String tag) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER)
        		.append("Event").append(DELIMITER).append(eventId).append(DELIMITER)
        		.append("Tag").append(DELIMITER).append(tag).append(DELIMITER)
        		.append("Nolimit").append(DELIMITER).toString();
    }

    public static String getEventCouponMapKey(final Long eventId, final String tag) {
    	return new StringBuilder(STRING_PREFIX).append(DELIMITER)
    			.append("Event").append(DELIMITER).append(eventId).append(DELIMITER)
        		.append("Tag").append(DELIMITER).append(tag).append(DELIMITER).toString();
    }

    public static String getCouponTotalQtyKey(final Long couponId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("Coupon").append(DELIMITER).append("Total").append(DELIMITER)
        		.append(couponId).append(DELIMITER).toString();
    }

    public static String getCouponIssuedQtyKey(final Long couponId) {
    	return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("Coupon").append(DELIMITER).append("Issued").append(DELIMITER)
    			.append(couponId).append(DELIMITER).toString();
    }

    public static String getUserPalyStartTimeKey(final Long userId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("UserPalyStartTime").append(DELIMITER)
        		.append("userId").append(DELIMITER).append(userId)
                .append(DELIMITER).toString();
    }

    public static String getUserIsPalyKey(final Long userId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("IsPaly").append(DELIMITER)
        		.append("userId").append(DELIMITER).append(userId)
                .append(DELIMITER).toString();
    }

    public static String getShortUrlKey(final String shortUrl) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("ShortUrl").append(DELIMITER)
        		.append("ShortUrl").append(DELIMITER).append(shortUrl)
                .append(DELIMITER).toString();
    }

    public static String getWechatXcApiTicket() {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER)
                .append("ApiTicket")
                .append(DELIMITER).toString();
    }

    public static String getWechatAccessTokenKey() {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER)
                .append("AccessToken")
                .append(DELIMITER).toString();
    }
    
    public static String getUserStoreReceiveMsgCount(final Long userId,final Long storeId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("userStoreReceiveMsgCount")
        		.append(DELIMITER).append("userId").append(DELIMITER).append(userId).append(DELIMITER).append("storeId").append(DELIMITER).append(storeId).toString();
    }

   public static String getUserTotalReceiveMsgCount(final Long userId) {
       return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("userTotalReceiveMsgCount")
       		.append(DELIMITER).append("userId").append(DELIMITER).append(userId).toString();
   }
}

