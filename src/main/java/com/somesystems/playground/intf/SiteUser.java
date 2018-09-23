package com.somesystems.playground.intf;

import com.somesystems.playground.beans.user.Kid;

public interface SiteUser {

    void setName(String name);

    String getName();

    String getSiteName();

    void setSiteName(String siteName);

    void setTicketNum(Double ticketNum);

    void setAge(Double age);

    Boolean getIsVipUser();

    void setIsVipUser(Boolean isVipUser);

    Double getTicketNum();

    Boolean getAcceptQueueWaiting();

    void setAcceptQueueWaiting(Boolean acceptQueueWaiting);

}
