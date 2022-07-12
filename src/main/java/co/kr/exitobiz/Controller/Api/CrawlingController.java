package co.kr.exitobiz.Controller.Api;

import co.kr.exitobiz.Crawling.*;
import co.kr.exitobiz.Vo.Crawling.CeciCrawVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class CrawlingController {

    @Autowired
    KStartUpCrawling kStartUpCrawling;

    @Autowired
    SbscCrawling sbscCrawling;

    @Autowired
    YouthSeoulCrawling youthSeoulCrawling;

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

    /* 서울창조경제혁신센터 */
    @Autowired
    SeoulCceiCrawling seoulCceiCrawling;

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

    /* 경남창조경제혁신센터 */
    @Autowired
    GyeongnamCceiCrawling gyeongnamCceiCrawling;

    /* 부산창조경제혁신센터 */
    @Autowired
    BusanCceiCrawling busanCceiCrawling;

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

    /* 경기테크노파크 */
    @Autowired
    GyeonggiGtpCrawling gyeonggiGtpCrawling;

    /* 경기창조경제혁신센터 */
    @Autowired
    GyeonggiCceiCrawling gyeonggiCceiCrawling;

    /* 인천창조경제혁신센터 */
    @Autowired
    IncheonCceiCrawling incheonCceiCrawling;

    /* 전북창조경제혁신센터 */
    @Autowired
    JeonbukCceiCrawling jeonbukCceiCrawling;

    /* 전남창조경제혁신센터 */
    @Autowired
    JeonnamCceiCrawling jeonnamCceiCrawling;

    /* 세종창조경제혁신센터 */
    @Autowired
    SejongCceiCrawling sejongCceiCrawling;

    /* 울산창조경제혁신센터 */
    @Autowired
    UlsanCceiCrawling ulsanCceiCrawling;

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

    /* 대구테크노파크 */
    @Autowired
    DaeguTpCrawling daeguTpCrawling;

    /* 대구창조경제혁신센터 */
    @Autowired
    DaeguCceiCrawling daeguCceiCrawling;

    /* 강원창조경제혁신센터 */
    @Autowired
    GangwonCceiCrawling gangwonCceiCrawling;

    /* 충북창조경제혁신센터 */
    @Autowired
    ChungbukCceiCrawling chungbukCceiCrawling;

    /* 충남창조경제혁신센터 */
    @Autowired
    ChungnamCceiCrawling chungnamCceiCrawling;

    /* 경북창조경제혁신센터 */
    @Autowired
    GyeongbukCceiCrawling gyeongbukCceiCrawling;

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

    /* 대전창조경제혁신센터 */
    @Autowired
    DaejeonCceiCrawling daejeonCceiCrawling;

    /* 광주창조경제혁신센터 */
    @Autowired
    GwangjuCceiCrawling gwangjuCceiCrawling;

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

    @Autowired
    NaraTestCrawling naraTestCrawling;

    @Autowired
    CeciCrawling ceciCrawling;

    @Autowired
    KpipaCrawling kpipaCrawling;

    @RequestMapping("/craw")
    @ResponseBody
    public void index() throws InterruptedException, IOException {
        System.out.println("컨트롤러시작테스트");

        System.out.println("컨트롤러완료테스트");
    }
}
