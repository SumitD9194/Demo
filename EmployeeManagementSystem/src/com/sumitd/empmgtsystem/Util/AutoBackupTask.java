package com.sumitd.empmgtsystem.Util;

import com.sumitd.empmgtsystem.Service.EmployeeService;

public class AutoBackupTask extends Thread {
    private EmployeeService service;

    public AutoBackupTask(EmployeeService service) {
        this.service = service;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(30000); // 30 seconds
                service.saveToFile();
                System.out.println("[Auto-Backup] Employee data saved.");
            } catch (InterruptedException e) {
                System.out.println("Backup thread interrupted.");
                break;
            }catch (Exception e) {
                throw new FileOperationException("Backup thread encountered an error", e);
            }
        }
    }
}
