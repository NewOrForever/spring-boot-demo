package com.demo.push.core.domain;

import java.util.List;

/**
 * ClassName:RasDomainBO
 * Package:com.demo.push.core.domain
 * Description:
 *
 * @Date:2023/4/4 17:02
 * @Author:qs@1.com
 */
public class RasDomainBO {
    private List<DomainListBO> hostList;
    /**
     * MD5({@link #hostList})
     */
    private String domainHash;

    public List<DomainListBO> getHostList() {
        return hostList;
    }

    public void setHostList(List<DomainListBO> hostList) {
        this.hostList = hostList;
    }

    public String getDomainHash() {
        return domainHash;
    }

    public void setDomainHash(String domainHash) {
        this.domainHash = domainHash;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RasDomainBO{");
        sb.append("rasHostList=").append(hostList);
        sb.append(", domainHash='").append(domainHash).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
