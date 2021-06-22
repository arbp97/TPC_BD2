package com.grupo_bd2.tpc;

import java.time.LocalDate;

import com.grupo_bd2.tpc.services.SaleService;

public class ReportTest {

    public static void main(String[] args) {

        System.out.println(SaleService.getInstance().firstReport(LocalDate.of(2021, 06, 9), LocalDate.of(2021, 06, 12)));

        System.out.println(SaleService.getInstance().thirdReport(LocalDate.of(2021, 06, 9), LocalDate.of(2021, 06, 12)));
        
        System.out.println(SaleService.getInstance().secondReport(LocalDate.of(2021, 06, 9), LocalDate.of(2021, 06, 12)));
    }

}
