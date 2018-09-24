package com.somesystems.playground.beans.user;

import com.somesystems.playground.intf.SiteUser;

public class Kid implements SiteUser {

    private String name;
    private Double age;
    private Double ticketNum;
    private Boolean isVipUser;
    private Boolean acceptQueueWaiting =Boolean.FALSE;
    private String siteName;

    public Kid(String name, Double age, Double ticketNum, Boolean isVipUser, String siteName,Boolean acceptQueueWaiting) {
        setName(name);
        setAge(age);
        setTicketNum(ticketNum);
        setIsVipUser(isVipUser);
        setSiteName(siteName);
        setAcceptQueueWaiting(acceptQueueWaiting);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }


    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    @Override
    public void setTicketNum(Double ticketNum) {
        this.ticketNum = ticketNum;
    }

    @Override
    public void setAge(Double age) {
        this.age = age;
    }

    @Override
    public Boolean getIsVipUser() {
        return isVipUser;
    }

    @Override
    public void setIsVipUser(Boolean isVipUser) {
        this.isVipUser = isVipUser;
    }

    @Override
    public Double getTicketNum() {
        return ticketNum;
    }

    public Boolean getAcceptQueueWaiting() {
        return acceptQueueWaiting;
    }

    public void setAcceptQueueWaiting(Boolean acceptQueueWaiting) {
        this.acceptQueueWaiting = acceptQueueWaiting;
    }
}
