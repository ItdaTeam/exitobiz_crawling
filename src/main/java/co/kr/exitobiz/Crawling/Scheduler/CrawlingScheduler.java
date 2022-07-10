package co.kr.exitobiz.Crawling.Scheduler;

import co.kr.exitobiz.Crawling.*;
import co.kr.exitobiz.Mappers.Api.CrawlingMapper;
import co.kr.exitobiz.Vo.Crawling.CeciCrawVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@EnableScheduling
public class CrawlingScheduler {

    @Autowired
    CrawlingMapper crawlingMapper;

    /* 케이스타트업 */
    @Autowired
    KStartUpCrawling kStartUpCrawling;

    /* 서울기업지원센터 */
    @Autowired
    SbscCrawling sbscCrawling;

    /* 서울청년정책 */
    @Autowired
    YouthSeoulCrawling youthSeoulCrawling;

    /* 서울사회적기업협의회 */
    @Autowired
    SeoulseCrawling seoulseCrawling;

    /* 서울산업진흥원 */
    @Autowired
    SbaCrawling sbaCrawling;

    /* 서울시 자영업지원센터 */
    @Autowired
    SeoulSbdcCrawling seoulSbdcCrawling;

    /* 서대문구-서울시립대학교 창업지원센터 */
    @Autowired
    SeoulBiUosCrawling seoulBiUosCrawling;

    /* 한국콘텐츠진흥원 */
    @Autowired
    SeoulKoccaCrawling seoulKoccaCrawling;

    /* 가톨릭대학교 창업대학 */
    @Autowired
    SeoulCatholicCrawling seoulCatholicCrawling;

    /* 양천디지털상상캠퍼스 */
    @Autowired
    SeoulYcstartupCrawling seoulYcstartupCrawling;

    /* 건국대학교 창업지원단 */
    @Autowired
    SeoulKonkukCrawling seoulKonkukCrawling;

    /* 건국대학교 창업보육센터 */
    @Autowired
    SeoulKkuCrawling seoulKkuCrawling;

    /* 서울창업카페상봉점 */
    @Autowired
    SeoulSangbongCrawling seoulSangbongCrawling;

    /* 서울창업디딤터 */
    @Autowired
    SeoulDidimentoCrawling seoulDidimentoCrawling;

    /* 한양대학교창업지원단 */
    @Autowired
    SeoulHanyangCrawling seoulHanyangCrawling;

    /* 카이스트창업원 */
    @Autowired
    SeoulKaistCrawling seoulKaistCrawling;

    /* 연세대학교 창업지원관 */
    @Autowired
    SeoulYonseiCrawling seoulYonseiCrawling;

    /* 한국여성벤처협회 */
    @Autowired
    SeoulKovwaCrawling seoulKovwaCrawling;

    /* 숭실대학교 창업지원단 */
    @Autowired
    SeoulSsuCrawling seoulSsuCrawling;

    /* 상명대학교 창업지원센터 */
    @Autowired
    SeoulSmuCrawling seoulSmuCrawling;

    /* 동덕여자대학교 창업지원센터 */
    @Autowired
    SeoulDongdukCrawling seoulDongdukCrawling;

    /* 고려대학교 크림창업지원단 */
    @Autowired
    SeoulPiportalCrawling seoulPiportalCrawling;

    /* 기업마당 */
    @Autowired
    SeoulBizinfoCrawling seoulBizinfoCrawling;

    /* 서울테크노파크 */
    @Autowired
    SeoulTpCrawling seoulTpCrawling;

    /* 지역문화진흥원 */
    @Autowired
    SeoulRcdaCrawling seoulRcdaCrawling;

    /* 북부여성발전센터 */
    @Autowired
    SeoulBukbuCrawling seoulBukbuCrawling;

    /* 한국문화예술교육진흥원 */
    @Autowired
    SeoulArteCrawling seoulArteCrawling;

    /* 서울시청년활동지원센터 */
    @Autowired
    SeoulSehubCrawling seoulSehubCrawling;

    /* 한국사회적기업진흥원 */
    @Autowired
    SeoulSocialEnterPrise seoulSocialEnterPrise;

    /* 서울 소셜벤처허브 */
    @Autowired
    SeoulSvhcCrawling seoulSvhcCrawling;

    /* 한국산업기술진흥원 */
    @Autowired
    SeoulKiatCrawling seoulKiatCrawling;

    /* 부산테크노파크 */
    @Autowired
    BusanBtpCrawling busanBtpCrawling;

    /* 경남테크노파크 */
    @Autowired
    GyeongnamGntpCrawling gyeongnamGntpCrawling;

    /* SMTECH */
    @Autowired
    SmtechCrawling smtechCrawling;

    /* 연구개발특구사업관리시스템 */
    @Autowired
    PmsCrawling pmsCrawling;

    /* 여성기업종합정보포털 */
    @Autowired
    WbizCrawling wbizCrawling;

    /* 한국여성과학기술인지원센터 */
    @Autowired
    WisetCrawling wisetCrawling;

    /* 한국출판문화산업진흥원 */
    @Autowired
    KpipaCrawling kpipaCrawling;

    /* 경기테크노파크 */
    @Autowired
    GyeonggiGtpCrawling gyeonggiGtpCrawling;

    /* 제주창조경제혁신센터 */
    @Autowired
    JejuCceiCrawling jejuCceiCrawling;

    /* 제주테크노파크 */
    @Autowired
    JejuTpCrawling jejuTpCrawling;

    /* 인천테크노파크 */
    @Autowired
    IncheonTpCrawling incheonTpCrawling;

    /* 전북테크노파크 */
    @Autowired
    JeonbukTpCrawling jeonbukTpCrawling;

    /* 전남테크노파크 */
    @Autowired
    JeonnamTpCrawling jeonnamTpCrawling;

    /* 세종테크노파크 */
    @Autowired
    SejongTpCrawling sejongTpCrawling;

    /* 울산테크노파크 */
    @Autowired
    UlsanTpCrawling ulsanTpCrawling;

    /* 강원테크노파크 */
    @Autowired
    GangwonTpCrawling gangwonTpCrawling;

    /* 충북테크노파크 */
    @Autowired
    ChungbukTpCrawling chungbukTpCrawling;

    /* 충남테크노파크 */
    @Autowired
    ChungnamTpCrawling chungnamTpCrawling;

    /* 경북테크노파크 */
    @Autowired
    GyeongbukTpCrawling gyeongbukTpCrawling;

    /* 대전테크노파크 */
    @Autowired
    DaejeonTpCrawling daejeonTpCrawling;

    /* 광주테크노파크 */
    @Autowired
    GwangjuTpCrawling gwangjuTpCrawling;

    /* 소상공인마당 창업지원 */
    @Autowired
    SbizCrawling sbizCrawling;

    /* 소상공인마당 정책자금 */
    @Autowired
    SbizFoundCrawling sbizFoundCrawling;

    /* 부산창업포탈 */
    @Autowired
    BusanStartupCrawling busanStartupCrawling;

    /* 부산디자인진흥원 */
    @Autowired
    BusanDcbCrawling busanDcbCrawling;

    /* 부산정보산업진흥원 */
    @Autowired
    BusanItCrawling busanItCrawling;

    /* 경남해외마케팅사업지원시스템 */
    @Autowired
    GyeongnamGoCrawling gyeongnamGoCrawling;

    /* 창원대산학협력단 */
    @Autowired
    GyeongnamChSanhakCrawling gyeongnamChSanhakCrawling;

    /* 김해의생명산업진흥원 */
    @Autowired
    GyeongnamGbiaCrawling gyeongnamGbiaCrawling;

    /* 창업산업진흥원 */
    @Autowired
    CwipCrawling cwipCrawling;

    /* 창원시사회적경제지원센터 */
    @Autowired
    GyeongnamCwsecCrawling gyeongnamCwsecCrawling;

    /* 광주디자인진흥원 */
    @Autowired
    GwangjuGdcCrawling gwangjuGdcCrawling;

    /* 광주정보문화산업진흥원 */
    @Autowired
    GwangjuGiconCrawling gwangjuGiconCrawling;

    /* 대전정보문화산업진흥원 */
    @Autowired
    DaejeonDiciaCrawling daejeonDiciaCrawling;

    /* 대전디자인진흥원 */
    @Autowired
    DaejeonDidpCrawling daejeonDidpCrawling;

    /* 강원디자인진흥원 */
    @Autowired
    GangwonGidpCrawling gangwonGidpCrawling;

    /* 강원정보문화산업진흥원 */
    @Autowired
    GangwonGicaCrawling gangwonGicaCrawling;

    /* 충남정보문화산업진흥원 */
    @Autowired
    ChungnamCtiaCrawling chungnamCtiaCrawling;

    /* 전주정보문화산업진흥원 */
    @Autowired
    JunjuJicaCrawling junjuJicaCrawling;

    /* 전남정보문화산업진흥원 */
    @Autowired
    JeonnamJciaCrawling jeonnamJciaCrawling;

    /* 경남문화예술진흥원 */
    @Autowired
    GyeongnamGcafCrawling gyeongnamGcafCrawling;

    /* 경남관광기업진원센터 */
    @Autowired
    GyeongnamTourCrawling gyeongnamTourCrawling;

    /* 경남경제진흥원 */
    @Autowired
    GyeongnamGnepaCrawling gyeongnamGnepaCrawling;

    /* 경남사회적경제통합지원센터 */
    @Autowired
    GyeongnamGseicCrawling gyeongnamGseicCrawling;

    /* 경남모두의경제 */
    @Autowired
    GyeongnamModuCrawling gyeongnamModuCrawling;

    /* 경기도경제과학진흥원 */
    @Autowired
    GyeonggiEgbizCrawling gyeonggiEgbizCrawling;

    /* 경기콘텐츠진흥원 */
    @Autowired
    GyeonggiGconCrawling gyeonggiGconCrawling;

    /* 한국관광공사 */
    @Autowired
    VisitKoreaCrawling visitKoreaCrawling;

    /* 서울R&D지원센터 */
    @Autowired
    SeoulRnbdCrawling seoulRnbdCrawling;

    /* 전북경제통상진흥원 */
    @Autowired
    JeonbukJbbaCrawling jeonbukJbbaCrawling;

    /* 서울대학교 창업지원단 */
    @Autowired
    SeoulSnuCrawling seoulSnuCrawling;

    /* 관악 사회적경제 */
    @Autowired
    SeoulGaseCrawling seoulGaseCrawling;

    /* 경북콘텐츠진흥원 */
    @Autowired
    GyeongbukGcubeCrawling gyeongbukGcubeCrawling;

    /* 문경시1인창조기업지원센터 */
    @Autowired
    GyeongbukMgbizCrawling gyeongbukMgbizCrawling;

    /* 포항테크노파크 */
    @Autowired
    GyeongbukPtpCrawling gyeongbukPtpCrawling;

    /* 창조경제혁신센터 */
    @Autowired
    CeciCrawling ceciCrawling;

    @Scheduled(cron = "0 0 0 * * ?")
    public void CrawlingGroup1() throws InterruptedException {
        /* 케이스타트업 */
        kStartUpCrawling.craw();

        /* 서울기업지원센터 */
        sbscCrawling.craw();

        /* 서울청년정책 */
        youthSeoulCrawling.craw();

        /* 서울사회적기업협의회 */
        seoulseCrawling.craw();

        /* 서울산업진흥원 */
        sbaCrawling.craw();

        /* 서울시 자영업지원센터 */
        seoulSbdcCrawling.craw();

        /* 서울시 청년활동지원센터 */
        seoulBiUosCrawling.craw();

        /* 한국콘텐츠진흥원 */
        seoulKoccaCrawling.craw();

        /* 서울R&D지원센터 */
        seoulRnbdCrawling.craw();

        /* 전북경제통상진흥원 */
        jeonbukJbbaCrawling.craw();
        System.out.println("--------그룹1 크롤링완료---------");
    }

    @Scheduled(cron = "0 5 0 * * ?")
    public void CrawlingGroup2() throws InterruptedException {

        /* 서울대학교 창업지원단 */
        seoulSnuCrawling.craw();

        /* 관악 사회적경제 */
        seoulGaseCrawling.craw();

        /* 가톨릭대학교 창업대학 */
        seoulCatholicCrawling.craw();

        /* 양천디지털상상캠퍼스 */
        seoulYcstartupCrawling.craw();

        /* 건국대학교 창업지원단 */
        seoulKonkukCrawling.craw();

        /* 건국대학교 창업보육센터 */
        seoulKkuCrawling.craw();

        /* 서울창업카페상봉점 */
        seoulSangbongCrawling.craw();

        /* 서울창업디딤터 */
        seoulDidimentoCrawling.craw();

        /* 한양대학교창업지원단 */
        seoulHanyangCrawling.craw();

        /* 카이스트창업원 */
        seoulKaistCrawling.craw();

        /* 연세대학교 창업지원관 */
        seoulYonseiCrawling.craw();

        System.out.println("--------그룹2 크롤링완료---------");
    }

    @Scheduled(cron = "0 10 0 * * ?")
    public void CrawlingGroup3() throws InterruptedException, IOException {
        /* 경북콘텐츠진흥원 */
        gyeongbukGcubeCrawling.craw();

        /* 문경시1인창조기업지원센터 */
        gyeongbukMgbizCrawling.craw();

        /* 포항테크노파크 */
        gyeongbukPtpCrawling.craw();

        /* 한국여성벤처협회 */
        seoulKovwaCrawling.craw();

        /* 숭실대학교 창업지원단 */
        seoulSsuCrawling.craw();

        /* 상명대학교 창업지원센터 */
        seoulSmuCrawling.craw();

        /* 동덕여자대학교 창업지원센터 */
        seoulDongdukCrawling.craw();

        /* 기업마당 */
        seoulBizinfoCrawling.craw();

        /* 지역문화진흥원 */
        seoulRcdaCrawling.craw();

        /* 소상공인마당 창업지원 */
        sbizCrawling.getCrawlingData();

        /* 소상공인마당 정책자금 */
        sbizFoundCrawling.getCrawlingData();

        /* 고려대학교 크림창업지원단 */
        seoulPiportalCrawling.craw();

        System.out.println("--------그룹3 크롤링완료---------");
    }

    @Scheduled(cron = "0 20 0 * * ?")
    public void CrawlingGroup4() throws InterruptedException {

        /* 북부여성발전센터 */
        seoulBukbuCrawling.craw();

        /* 한국문화예술교육진흥원 */
        seoulArteCrawling.craw();

        /* 서울시청년활동지원센터 */
        seoulSehubCrawling.craw();

        /* 한국사회적기업진흥원 */
        seoulSocialEnterPrise.craw();

        /* 서울 소셜벤처허브 */
        seoulSvhcCrawling.craw();

        /* 부산테크노파크 */
        busanBtpCrawling.craw();

        /* 경남테크노파크 */
        gyeongnamGntpCrawling.craw();

        /* SMTECH */
        smtechCrawling.craw();

        /* 연구개발특구사업관리시스템 */
        pmsCrawling.craw();

        /* 여성기업종합정보포털 */
        wbizCrawling.craw();

        /* 한국산업기술진흥원 */
        seoulKiatCrawling.craw();
        System.out.println("--------그룹4 크롤링완료---------");
    }

    @Scheduled(cron = "0 25 0 * * ?")
    public void CrawlingGroup5() throws InterruptedException {
        CeciCrawVo seoul = new CeciCrawVo("서울창조경제혁신센터","https://ccei.creativekorea.or.kr/seoul",1,"C02");
        CeciCrawVo gyeongnam = new CeciCrawVo("경남창조경제혁신센터","https://ccei.creativekorea.or.kr/gyeongnam",1,"C055");
        CeciCrawVo busan = new CeciCrawVo("부산창조경제혁신센터","https://ccei.creativekorea.or.kr/busan",1,"C051");
        CeciCrawVo gyeonggi = new CeciCrawVo("경기창조경제혁신센터","https://ccei.creativekorea.or.kr/gyeonggi",1,"C031");
        CeciCrawVo incheon = new CeciCrawVo("인천창조경제혁신센터","https://ccei.creativekorea.or.kr/incheon",1,"C032");
        CeciCrawVo jeonbuk = new CeciCrawVo("전북창조경제혁신센터","https://ccei.creativekorea.or.kr/jeonbuk",1,"C063");
        CeciCrawVo jeonnam = new CeciCrawVo("전남창조경제혁신센터","https://ccei.creativekorea.or.kr/jeonnam",1,"C061");
        CeciCrawVo sejong = new CeciCrawVo("세종창조경제혁신센터","https://ccei.creativekorea.or.kr/sejong",1,"C044");
        CeciCrawVo ulsan = new CeciCrawVo("울산창조경제혁신센터","https://ccei.creativekorea.or.kr/ulsan",1,"C052");
        CeciCrawVo gyeongbuk = new CeciCrawVo("경북창조경제혁신센터","https://ccei.creativekorea.or.kr/gyeongbuk",1,"C054");
        CeciCrawVo gwangju = new CeciCrawVo("광주창조경제혁신센터","https://ccei.creativekorea.or.kr/gwangju",1,"C062");
        CeciCrawVo chungbuk = new CeciCrawVo("충북창조경제혁신센터","https://ccei.creativekorea.or.kr/chungbuk",1,"C043");

        /* 서울창조경제혁신센터 */
        ceciCrawling.craw(seoul);

        /* 경남창조경제혁신센터 */
        ceciCrawling.craw(gyeongnam);

        /* 부산창조경제혁신센터 */
        ceciCrawling.craw(busan);

        /* 경기창조경제혁신센터 */
        ceciCrawling.craw(gyeonggi);

        /* 인천창조경제혁신센터 */
        ceciCrawling.craw(incheon);

        /* 전북창조경제혁신센터 */
        ceciCrawling.craw(jeonbuk);

        /* 전남창조경제혁신센터 */
        ceciCrawling.craw(jeonnam);

        /* 세종창조경제혁신센터 */
        ceciCrawling.craw(sejong);

        /* 울산창조경제혁신센터 */
        ceciCrawling.craw(ulsan);

        /* 경북창조경제혁신센터 */
        ceciCrawling.craw(gyeongbuk);

        /* 광주창조경제혁신센터 */
        ceciCrawling.craw(gwangju);

        /* 충북창조경제혁신센터 */
        ceciCrawling.craw(chungbuk);

        /* 제주창조경제혁신센터 */
        jejuCceiCrawling.craw();

        System.out.println("--------그룹5 크롤링완료---------");
    }

    @Scheduled(cron = "0 30 0 * * ?")
    public void CrawlingGroup6() throws InterruptedException {


        /* 한국여성과학기술인지원센터 */
        wisetCrawling.craw();

        /* 한국출판문화산업진흥원 */
        kpipaCrawling.craw();

        /* 제주테크노파크 */
        jejuTpCrawling.craw();

        /* 경기테크노파크 */
        gyeonggiGtpCrawling.craw();

        /* 서울테크노파크 */
        seoulTpCrawling.craw();

        /* 전북테크노파크 */
        jeonbukTpCrawling.craw();

        /* 전남테크노파크 */
        jeonnamTpCrawling.craw();

        /* 세종테크노파크 */
        sejongTpCrawling.craw();

        /* 울산테크노파크 */
        ulsanTpCrawling.craw();

        /* 인천테크노파크 */
        incheonTpCrawling.craw();

        System.out.println("--------그룹6 크롤링완료---------");
    }

    @Scheduled(cron = "0 35 0 * * ?")
    public void CrawlingGroup7() throws InterruptedException {

        /* 강원테크노파크 */
        gangwonTpCrawling.craw();

        /* 충북테크노파크 */
        chungbukTpCrawling.craw();

        /* 충남테크노파크 */
        chungnamTpCrawling.craw();

        /* 경북테크노파크 */
        gyeongbukTpCrawling.craw();

        /* 대전테크노파크 */
        daejeonTpCrawling.craw();

        /* 광주테크노파크 */
        gwangjuTpCrawling.craw();

        /* 경기도경제과학진흥원 */
        gyeonggiEgbizCrawling.craw();

        /* 경기콘텐츠진흥원 */
        gyeonggiGconCrawling.craw();

        /* 한국관광공사 */
        visitKoreaCrawling.craw();

        System.out.println("--------그룹7 크롤링완료---------");
    }

    @Scheduled(cron = "0 45 00 * * ?")
    public void CrawlingGroup8() throws InterruptedException {


        /* 광주정보문화산업진흥원 */
        gwangjuGiconCrawling.craw();

        /* 대전정보문화산업진흥원 */
        daejeonDiciaCrawling.craw();

        /* 대전디자인진흥원 */
        daejeonDidpCrawling.craw();

        /* 강원디자인진흥원 */
        gangwonGidpCrawling.craw();

        /* 강원정보문화산업진흥원 */
        gangwonGicaCrawling.craw();

        /* 충남정보문화산업진흥원 */
        chungnamCtiaCrawling.craw();

        /* 전주정보문화산업진흥원 */
        junjuJicaCrawling.craw();

        /* 전남정보문화산업진흥원 */
        jeonnamJciaCrawling.craw();

        /* 경남문화예술진흥원 */
        gyeongnamGcafCrawling.craw();

        /* 경남관광기업진원센터 */
        gyeongnamTourCrawling.craw();

        /* 경남경제진흥원 */
        gyeongnamGnepaCrawling.craw();

        /* 경남사회적경제통합지원센터 */
        gyeongnamGseicCrawling.craw();

        /* 부산창업포탈 */
        busanStartupCrawling.craw();

        /* 부산디자인진흥원 */
        busanDcbCrawling.craw();

        /* 부산정보산업진흥원 */
        busanItCrawling.craw();

        /* 창원대산학협력단 */
        gyeongnamChSanhakCrawling.craw();

        /* 김해의생명산업진흥원 */
        gyeongnamGbiaCrawling.craw();

        /* 창업산업진흥원 */
        cwipCrawling.craw();

        /* 창원시사회적경제지원센터 */
        gyeongnamCwsecCrawling.craw();

        /* 경남모두의경제 */
        gyeongnamModuCrawling.craw();

        /* 경남해외마케팅사업지원시스템 */
        gyeongnamGoCrawling.craw();

        /* 광주디자인진흥원 */
        gwangjuGdcCrawling.craw();


        System.out.println("--------그룹8 크롤링완료---------");
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void CleanUpData() throws InterruptedException {
        crawlingMapper.removeLNew();
        crawlingMapper.removeRNew();
        crawlingMapper.removeBlank();

        System.out.println("--------데이터 정리 완료---------");
    }

}
