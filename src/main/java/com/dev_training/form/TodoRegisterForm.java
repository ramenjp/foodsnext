package com.dev_training.form;

import com.dev_training.validator.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Todo登録フォーム。
 */
public class TodoRegisterForm implements Serializable {

    @NotBlank
    @Size(max = 45, message = "{error.size.max}")
    private String title;

    @NotBlank
    @Size(max = 2000, message = "{error.size.max}")
    private String detail;

    @Size(max = 2000, message = "{error.size.max}")
    private String remarks;

    @Date
    private String startDate;

    @Date
    private String endDate;

    @NotBlank
    private String issuePersonId;

    @NotBlank
    private String personInChargeId;

    @NotBlank
    private String selectedStatus;

    @NotBlank
    private String selectedPriority;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getIssuePersonId() {
        return issuePersonId;
    }

    public void setIssuePersonId(String issuePersonId) {
        this.issuePersonId = issuePersonId;
    }

    public String getPersonInChargeId() {
        return personInChargeId;
    }

    public void setPersonInChargeId(String personInChargeId) {
        this.personInChargeId = personInChargeId;
    }

    public String getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(String selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public String getSelectedPriority() {
        return selectedPriority;
    }

    public void setSelectedPriority(String selectedPriority) {
        this.selectedPriority = selectedPriority;
    }
}
