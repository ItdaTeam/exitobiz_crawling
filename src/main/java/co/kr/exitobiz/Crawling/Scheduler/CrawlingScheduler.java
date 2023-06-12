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

    /* 제주창조경제혁신센터 */
    @Autowired
    JejuCceiCrawling jejuCceiCrawling;

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

    /* 창조경제혁신센터 */
    @Autowired
    CeciCrawling ceciCrawling;

    /* 창원산업진흥원 */
    @Autowired
    ChangwonCwipCrawling changwonCwipCrawling;

    /* 한국무역협회 */
    @Autowired
    KitaCrawling kitaCrawling;

    /* 창업진흥원 */
    @Autowired
    KisedCrawling kisedCrawling;

    /* 송파구일자리통합지원센터 */
    @Autowired
    SongpaCrawling songpaCrawling;

    /* 한국테크노파크 */
    @Autowired
    KoreaTpCrawling koreaTpCrawling;

    /** KOTRA*/
    @Autowired
    KortaCrawling kortaCrawling;

    /**소상공인마당Web */
    @Autowired
    SbizWebCrawling sbizWebCrawling;

    /** 기술보호울타리 */
    @Autowired
    UltariCrawling ultariCrawling;

    /** 중소기업해외전시포탈 */
    @Autowired
    SmesCrawling smesCrawling;


    /**
     * 현대 일렉트로닉스 전용 크롤링 대상들
     */

    /** 한국에너지공단 */
    @Autowired
    KnrecCrawling knrecCrawling;

    /** 한국환경공단*/
    @Autowired
    KecoCrawling kecoCrawling;

    /** 경기환경에너지진흥원 */
    @Autowired
    GgEnergyCrawling ggenergyCrawling;

    /** 충남에너지센터   */
    @Autowired
    CnEnergyCrawling cnEnergyCrawling;

    /** 당진에너지센터 */
    @Autowired
    DjEnercyCrawling djEnercyCrawling;

    /*  한국화학융합시험연구원 */
    @Autowired
    KoreaTrCrawling koreaTrCrawling;

    /** 녹색에너지연구원 */
    @Autowired
    GreenEICrawling greenEICrawling;

    /** (사)한국신,재생에너지협회 */
    @Autowired
    KnreaCrawling knreaCrawling;

    /** 안산환경재단 */
    @Autowired
    AnsanCrawling ansanCrawling;

    /** 스마트제조혁신추진단 */
    @Autowired
    SmartFactoryCrawling smartFactoryCrawling;

    /** 중소기업중앙회 - 스마트공장 */
    @Autowired
    KbizFactoryCrawling kbizFactoryCrawling;

    /** 한국생산기술연구원*/
    @Autowired
    KitechCrawling kitechCrawling;

    /** 환경부 */
    @Autowired
    MinistryCrawling ministryCrawling;

    /** 에너지기술평가원*/
    @Autowired
    KetepCrawling ketepCrawling;

    /** 정보통신산업진흥원*/
    @Autowired
    NipaCrawling nipaCrawling;

    /** 한국환경산업기술원 */
    @Autowired
    KoneticCrawling koneticCrawling;

    /** 캠틱종합기술원 */
    @Autowired
    CamticCrawling camticCrawling;

    /** 경북구미강소특구*/
    @Autowired
    KumohCrawling kumohCrawling;

    /** 연구개발특구진흥재단 */
    @Autowired
    InnopolisCrawling innopolisCrawling;

    /** 한국산업단지공단*/
    @Autowired
    KicoxCrawling kicoxCrawling;

    /** 강릉과학산업진흥원*/
    @Autowired
    GsipaACrawling gsipaACrawling;
    /** 한국산업인력공단 */
    @Autowired
    HrdkoreaCrawling hrdkoreaCrawling;


    /** 현대전용 끝 */




//        테스트용
//    @Scheduled(cron = "0 45 15 * * *")
    public void CrawlingGroup10() throws InterruptedException, IOException {
        seoulBizinfoCrawling.craw();
    }


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

        /* 서울시 청년활동지원센터 */
        seoulBiUosCrawling.craw();

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

        /* 건국대학교 창업지원단 */
        seoulKonkukCrawling.craw();

        /* 서울창업카페상봉점 */
//        seoulSangbongCrawling.craw();

        /* 서울창업디딤터 */
//        seoulDidimentoCrawling.craw();

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

        /* 한국여성벤처협회 */
        seoulKovwaCrawling.craw();

        /* 숭실대학교 창업지원단 */
        seoulSsuCrawling.craw();

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

    @Scheduled(cron = "0 15 0 * * ?")
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

        /* 여성기업종합정보포털 */
        wbizCrawling.craw();
        System.out.println("--------그룹4 크롤링완료---------");
    }

    @Scheduled(cron = "0 20 0 * * ?")
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
        CeciCrawVo daegu = new CeciCrawVo("대구창조경제혁신센터","https://ccei.creativekorea.or.kr/daegu",1,"C053");
        CeciCrawVo gangwon = new CeciCrawVo("강원창조경제혁신센터","https://ccei.creativekorea.or.kr/gangwon",1,"C033");
        CeciCrawVo chungnam = new CeciCrawVo("충남창조경제혁신센터","https://ccei.creativekorea.or.kr/chungnam",1,"C041");
        CeciCrawVo daejeon = new CeciCrawVo("대전창조경제혁신센터","https://ccei.creativekorea.or.kr/daejeon",1,"C042");
        CeciCrawVo jeju = new CeciCrawVo("제주창조경제혁신센터","https://ccei.creativekorea.or.kr/jeju",1,"C042");

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
        
        /* 강원창조경제혁신센터 */
        ceciCrawling.craw(gangwon);

        /* 대구창조경제혁신센터 */
        ceciCrawling.craw(daegu);

        /* 충남창조경제혁신센터 */
        ceciCrawling.craw(chungnam);

        /* 대전창조경제혁신센터 */
        ceciCrawling.craw(daejeon);

        /* 제주창조경제혁신센터 */
        ceciCrawling.craw(jeju);
//        jejuCceiCrawling.craw();

        System.out.println("--------그룹5 크롤링완료---------");
    }

    @Scheduled(cron = "0 25 0 * * ?")
    public void CrawlingGroup6() throws InterruptedException {

        /* 한국출판문화산업진흥원 */
        kpipaCrawling.craw();

        /* 경기도경제과학진흥원 */
        gyeonggiEgbizCrawling.craw();

        /* 경기콘텐츠진흥원 */
        gyeonggiGconCrawling.craw();

        /* 한국관광공사 */
        visitKoreaCrawling.craw();


        System.out.println("--------그룹6 크롤링완료---------");
    }

    @Scheduled(cron = "0 30 0 * * ?")
    public void CrawlingGroup8() throws InterruptedException {


        /* 광주정보문화산업진흥원 */
        gwangjuGiconCrawling.craw();

        /* 대전정보문화산업진흥원 */
        daejeonDiciaCrawling.craw();


        /* 강원디자인진흥원 */
        gangwonGidpCrawling.craw();

        /* 충남정보문화산업진흥원 */
        chungnamCtiaCrawling.craw();

        /* 전주정보문화산업진흥원 */
        junjuJicaCrawling.craw();

        /* 전남정보문화산업진흥원 */
        jeonnamJciaCrawling.craw();

        /* 경남문화예술진흥원 */
        gyeongnamGcafCrawling.craw();


        /* 경남사회적경제통합지원센터 */
        gyeongnamGseicCrawling.craw();

        /* 부산창업포탈 */
        busanStartupCrawling.craw();

        /* 부산디자인진흥원 */
        busanDcbCrawling.craw();

        /* 부산정보산업진흥원 */
        busanItCrawling.craw();

        /* 김해의생명산업진흥원 */
        gyeongnamGbiaCrawling.craw();

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

    @Scheduled(cron = "0 35 0 * * ?")
    public void CrawlingGroup9() throws InterruptedException {
        /* 창원산업진흥원 */
        changwonCwipCrawling.craw();

        /* 한국무역협회 */
        kitaCrawling.craw();

        /* 한국테크노파크 */
        koreaTpCrawling.craw();

        /** KOTRA*/
        kortaCrawling.craw();

        /**소상공인마당Web */
        sbizWebCrawling.craw();

        /** 기술보호울타리 */
        ultariCrawling.craw();

        /** 중소기업해외전시포탈 */
        smesCrawling.craw();

        System.out.println("--------그룹9 크롤링완료---------");
    }

    @Scheduled(cron ="0 40 0 * * ?")
    public void CrawlingHyunDai() throws InterruptedException{

        /** 한국환경공단*/
        kecoCrawling.craw();


        /** 경기환경에너지진흥원 */
        ggenergyCrawling.craw();

        /** 충남에너지센터   */
        cnEnergyCrawling.craw();

        /** 당진에너지센터 */
        djEnercyCrawling.craw();

        /*  한국화학융합시험연구원 */
        koreaTrCrawling.craw();
        /** 녹색에너지연구원 */
        greenEICrawling.craw();

        /** (사)한국신,재생에너지협회 */
        knreaCrawling.craw();

        /** 안산환경재단 */
        ansanCrawling.craw();

        /** 스마트제조혁신추진단 */
        smartFactoryCrawling.craw();

        /** 중소기업중앙회 - 스마트공장 */
        kbizFactoryCrawling.craw();

        /** 한국생산기술연구원*/
        kitechCrawling.craw();

        /** 환경부 */
        ministryCrawling.craw();

        /** 에너지기술평가원*/
        ketepCrawling.craw();

        /** 정보통신산업진흥원*/
        nipaCrawling.craw();//e

        /** 한국에너지공단 */
        knrecCrawling.craw();

        /** 한국환경산업기술원 */
        koneticCrawling.craw();

        /** 캠틱종합기술원 */
        camticCrawling.craw();

        /** 경북구미강소특구*/
        kumohCrawling.craw();

        /** 연구개발특구진흥재단 */
        innopolisCrawling.craw();

        /** 한국산업단지공단*/
        kicoxCrawling.craw();

        /** 강릉과학산업진흥원*/
        gsipaACrawling.craw();

        /** 한국산업인력공단 */
        hrdkoreaCrawling.craw();
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void CleanUpData() throws InterruptedException {
        crawlingMapper.removeLNew();
        crawlingMapper.removeRNew();
//        crawlingMapper.removeBlank();

        System.out.println("--------데이터 정리 완료---------");
    }

}
