package com.grupo_bd2.tpc;

import java.time.LocalDate;

import com.grupo_bd2.tpc.services.SaleService;

public class ReportTest {

    public static void main(String[] args) {

        System.out.println("PRIMER REPORTE\n");
        System.out.println(SaleService.getInstance().firstReport(LocalDate.of(2021, 06, 9), LocalDate.of(2021, 06, 12)));

        System.out.println("SEGUNDO REPORTE\n");
        System.out.println(SaleService.getInstance().secondReport(LocalDate.of(2020, 06, 9), LocalDate.of(2021, 12, 12)));

        System.out.println("TERCER REPORTE\n");
        System.out.println(SaleService.getInstance().thirdReport(LocalDate.of(2021, 06, 9), LocalDate.of(2021, 06, 12)));

        System.out.println("CUARTO REPORTE\n");
        System.out.println(SaleService.getInstance().FourthReport(LocalDate.of(2020, 06, 9), LocalDate.of(2021, 06, 30)));

        System.out.println("PRIMER RANKING\n");
        System.out.println(SaleService.getInstance().firstRanking());

        System.out.println("SEGUNDO RANKING\n");
        System.out.println(SaleService.getInstance().secondRanking());
    }

}
