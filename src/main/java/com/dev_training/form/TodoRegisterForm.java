package com.dev_training.form;

import com.dev_training.validator.Date;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Todo登録フォーム。
 */
@Data
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
    private int issuePersonId;

    @NotBlank
    private int personInChargeId;

    @NotBlank
    private String status;

    @NotBlank
    private String priority;

    @AssertTrue(message = "validation.invalidDate")
    public boolean isValidDate() {
        if (startDate == null) return true;
        if (endDate == null) return true;
        if (startDate.compareTo(endDate) <= 0) return true;
        return false;
    }

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

    public int getIssuePersonId() {
        return issuePersonId;
    }

    public void setIssuePersonId(int issuePersonId) {
        this.issuePersonId = issuePersonId;
    }

    public int getPersonInChargeId() {
        return personInChargeId;
    }

    public void setPersonInChargeId(int personInChargeId) {
        this.personInChargeId = personInChargeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
