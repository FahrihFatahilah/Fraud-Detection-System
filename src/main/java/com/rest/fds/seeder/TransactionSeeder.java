package com.rest.fds.seeder;

import com.rest.fds.entity.EntityTransactionType;
import com.rest.fds.entity.UserAgentEntity;
import com.rest.fds.repository.TransactionTypeRepository;
import com.rest.fds.repository.UserAgentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class TransactionSeeder implements CommandLineRunner {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    private final TransactionTypeRepository transactionTypeRepository;

    private final UserAgentRepository userAgentRepository;

    public TransactionSeeder(TransactionTypeRepository transactionTypeRepository, UserAgentRepository userAgentRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
        this.userAgentRepository = userAgentRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        if(userAgentRepository.count() == 0){
            List<String> userAgentNames = Arrays.asList(
                    "01h4x.com",
                    "360Spider",
                    "404checker",
                    "404enemy",
                    "80legs",
                    "ADmantX",
                    "AIBOT",
                    "ALittle Client",
                    "ASPSeek",
                    "Abonti",
                    "Aboundex",
                    "Aboundexbot",
                    "Acunetix",
                    "AdsTxtCrawlerTP",
                    "AfD-Verbotsverfahren",
                    "AhrefsBot",
                    "AiHitBot",
                    "Aipbot",
                    "Alexibot",
                    "AllSubmitter",
                    "Alligator",
                    "AlphaBot",
                    "Anarchie",
                    "Anarchy",
                    "Anarchy99",
                    "Ankit",
                    "Anthill",
                    "Apexoo",
                    "Aspiegel",
                    "Asterias",
                    "Atomseobot",
                    "Attach",
                    "AwarioBot",
                    "AwarioRssBot",
                    "AwarioSmartBot",
                    "BBBike",
                    "BDCbot",
                    "BDFetch",
                    "BLEXBot",
                    "BackDoorBot",
                    "BackStreet",
                    "BackWeb",
                    "Backlink-Ceck",
                    "BacklinkCrawler",
                    "BacklinksExtendedBot",
                    "Badass",
                    "Bandit",
                    "Barkrowler",
                    "BatchFTP",
                    "Battleztar Bazinga",
                    "BetaBot",
                    "Bigfoot",
                    "Bitacle",
                    "BlackWidow",
                    "Black Hole",
                    "Blackboard",
                    "Blow",
                    "BlowFish",
                    "Boardreader",
                    "Bolt",
                    "BotALot",
                    "Brandprotect",
                    "Brandwatch",
                    "Buck",
                    "Buddy",
                    "BuiltBotTough",
                    "BuiltWith",
                    "Bullseye",
                    "BunnySlippers",
                    "BuzzSumo",
                    "Bytespider",
                    "CATExplorador",
                    "CCBot",
                    "CODE87",
                    "CSHttp",
                    "Calculon",
                    "CazoodleBot",
                    "Cegbfeieh",
                    "CensysInspect",
                    "ChatGPT-User",
                    "CheTeam",
                    "CheeseBot",
                    "CherryPicker",
                    "ChinaClaw",
                    "Chlooe",
                    "Citoid",
                    "Claritybot",
                    "ClaudeBot",
                    "Cliqzbot",
                    "Cloud mapping",
                    "Cocolyzebot",
                    "Cogentbot",
                    "Collector",
                    "Copier",
                    "CopyRightCheck",
                    "Copyscape",
                    "Cosmos",
                    "Craftbot",
                    "Crawling at Home Project",
                    "CrazyWebCrawler",
                    "Crescent",
                    "CrunchBot",
                    "Curious",
                    "Custo",
                    "CyotekWebCopy",
                    "DBLBot",
                    "DIIbot",
                    "DSearch",
                    "DTS Agent",
                    "DataCha0s",
                    "DatabaseDriverMysqli",
                    "Demon",
                    "Deusu",
                    "Devil",
                    "Digincore",
                    "DigitalPebble",
                    "Dirbuster",
                    "Disco",
                    "Discobot",
                    "Discoverybot",
                    "Dispatch",
                    "DittoSpyder",
                    "DnBCrawler-Analytics",
                    "DnyzBot",
                    "DomCopBot",
                    "DomainAppender",
                    "DomainCrawler",
                    "DomainSigmaCrawler",
                    "DomainStatsBot",
                    "Domains Project",
                    "Dotbot",
                    "Download Wonder",
                    "Dragonfly",
                    "Drip",
                    "ECCP/1.0",
                    "EMail Siphon",
                    "EMail Wolf",
                    "EasyDL",
                    "Ebingbong",
                    "Ecxi",
                    "EirGrabber",
                    "EroCrawler",
                    "Evil",
                    "Exabot",
                    "Express WebPictures",
                    "ExtLinksBot",
                    "Extractor",
                    "ExtractorPro",
                    "Extreme Picture Finder",
                    "EyeNetIE",
                    "Ezooms",
                    "FDM",
                    "FHscan",
                    "FacebookBot",
                    "FemtosearchBot",
                    "Fimap",
                    "Firefox/7.0",
                    "FlashGet",
                    "Flunky",
                    "Foobot",
                    "Freeuploader",
                    "FrontPage",
                    "Fuzz",
                    "FyberSpider",
                    "Fyrebot",
                    "G-i-g-a-b-o-t",
                    "GPTBot",
                    "GT::WWW",
                    "GalaxyBot",
                    "Genieo",
                    "GermCrawler",
                    "GetRight",
                    "GetWeb",
                    "Getintent",
                    "Gigabot",
                    "Go!Zilla",
                    "Go-Ahead-Got-It",
                    "GoZilla",
                    "Gotit",
                    "GrabNet",
                    "Grabber",
                    "Grafula",
                    "GrapeFX",
                    "GrapeshotCrawler",
                    "GridBot",
                    "HEADMasterSEO",
                    "HMView",
                    "HTMLparser",
                    "HTTP::Lite",
                    "HTTrack",
                    "Haansoft",
                    "HaosouSpider",
                    "Harvest",
                    "Havij",
                    "Heritrix",
                    "Hloader",
                    "HonoluluBot",
                    "Humanlinks",
                    "HybridBot",
                    "IDBTE4M",
                    "IDBot",
                    "IRLbot",
                    "Iblog",
                    "Id-search",
                    "IlseBot",
                    "Image Fetch",
                    "Image Sucker",
                    "ImagesiftBot",
                    "IndeedBot",
                    "Indy Library",
                    "InfoNaviRobot",
                    "InfoTekies",
                    "Information Security Team InfraSec Scanner",
                    "InfraSec Scanner",
                    "Intelliseek",
                    "InterGET",
                    "InternetMeasurement",
                    "InternetSeer",
                    "Internet Ninja",
                    "Iria",
                    "Iskanie",
                    "IstellaBot",
                    "JOC Web Spider",
                    "JamesBOT",
                    "Jbrofuzz",
                    "JennyBot",
                    "JetCar",
                    "Jetty",
                    "JikeSpider",
                    "Joomla",
                    "Jorgee",
                    "JustView",
                    "Jyxobot",
                    "Kenjin Spider",
                    "Keybot Translation-Search-Machine",
                    "Keyword Density",
                    "Kinza",
                    "Kozmosbot",
                    "LNSpiderguy",
                    "LWP::Simple",
                    "Lanshanbot",
                    "Larbin",
                    "Leap",
                    "LeechFTP",
                    "LeechGet",
                    "LexiBot",
                    "Lftp",
                    "LibWeb",
                    "Libwhisker",
                    "LieBaoFast",
                    "Lightspeedsystems",
                    "Likse",
                    "LinkScan",
                    "LinkWalker",
                    "Linkbot",
                    "LinkextractorPro",
                    "LinkpadBot",
                    "LinksManager",
                    "LinqiaMetadataDownloaderBot",
                    "LinqiaRSSBot",
                    "LinqiaScrapeBot",
                    "Lipperhey",
                    "Lipperhey Spider",
                    "Litemage_walker",
                    "Lmspider",
                    "Ltx71",
                    "MFC_Tear_Sample",
                    "MIDown tool",
                    "MIIxpc",
                    "MJ12bot",
                    "MQQBrowser",
                    "MSFrontPage",
                    "MSIECrawler",
                    "MTRobot",
                    "Mag-Net",
                    "Magnet",
                    "Mail.RU_Bot",
                    "Majestic-SEO",
                    "Majestic12",
                    "Majestic SEO",
                    "MarkMonitor",
                    "MarkWatch",
                    "Mass Downloader",
                    "Masscan",
                    "Mata Hari",
                    "MauiBot",
                    "Mb2345Browser",
                    "MeanPath Bot",
                    "Meanpathbot",
                    "Mediatoolkitbot",
                    "MegaIndex.ru",
                    "Metauri",
                    "MicroMessenger",
                    "Microsoft Data Access",
                    "Microsoft URL Control",
                    "Minefield",
                    "Mister PiX",
                    "Moblie Safari",
                    "Mojeek",
                    "Mojolicious",
                    "MolokaiBot",
                    "Morfeus Fucking Scanner",
                    "Mozlila",
                    "Mr.4x3",
                    "Msrabot",
                    "Musobot",
                    "NICErsPRO",
                    "NPbot",
                    "Name Intelligence",
                    "Nameprotect",
                    "Navroad",
                    "NearSite",
                    "Needle",
                    "Nessus",
                    "NetAnts",
                    "NetLyzer",
                    "NetMechanic",
                    "NetSpider",
                    "NetZIP",
                    "Net Vampire",
                    "Netcraft",
                    "Nettrack",
                    "Netvibes",
                    "NextGenSearchBot",
                    "Nibbler",
                    "Niki-bot",
                    "Nikto",
                    "NimbleCrawler",
                    "Nimbostratus",
                    "Ninja",
                    "Nmap",
                    "Nuclei",
                    "Nutch",
                    "Octopus",
                    "Offline Explorer",
                    "Offline Navigator",
                    "OnCrawl",
                    "OpenLinkProfiler",
                    "OpenVAS",
                    "Openfind",
                    "Openvas",
                    "OrangeBot",
                    "OrangeSpider",
                    "OutclicksBot",
                    "OutfoxBot",
                    "PECL::HTTP",
                    "PHPCrawl",
                    "POE-Component-Client-HTTP",
                    "PageAnalyzer",
                    "PageGrabber",
                    "PageScorer",
                    "PageThing.com",
                    "Page Analyzer",
                    "Pandalytics",
                    "Panscient",
                    "Papa Foto",
                    "Pavuk",
                    "PeoplePal",
                    "Petalbot",
                    "Pi-Monster",
                    "Picscout",
                    "Picsearch",
                    "PictureFinder",
                    "Piepmatz",
                    "Pimonster",
                    "Pixray",
                    "PleaseCrawl",
                    "Pockey",
                    "ProPowerBot",
                    "ProWebWalker",
                    "Probethenet",
                    "Proximic",
                    "Psbot",
                    "Pu_iN",
                    "Pump",
                    "PxBroker",
                    "PyCurl",
                    "QueryN Metasearch",
                    "Quick-Crawler",
                    "RSSingBot",
                    "Rainbot",
                    "RankActive",
                    "RankActiveLinkBot",
                    "RankFlex",
                    "RankingBot",
                    "RankingBot2",
                    "Rankivabot",
                    "RankurBot",
                    "Re-re",
                    "ReGet",
                    "RealDownload",
                    "Reaper",
                    "RebelMouse",
                    "Recorder",
                    "RedesScrapy",
                    "RepoMonkey",
                    "Ripper",
                    "RocketCrawler",
                    "Rogerbot",
                    "SBIder",
                    "SEOkicks",
                    "SEOkicks-Robot",
                    "SEOlyt",
                    "SEOlyticsCrawler",
                    "SEOprofiler",
                    "SEOstats",
                    "SISTRIX",
                    "SMTBot",
                    "SalesIntelligent",
                    "ScanAlert",
                    "Scanbot",
                    "ScoutJet",
                    "Scrapy",
                    "Screaming",
                    "ScreenerBot",
                    "ScrepyBot",
                    "Searchestate",
                    "SearchmetricsBot",
                    "Seekport",
                    "SeekportBot",
                    "SemanticJuice",
                    "Semrush",
                    "SemrushBot",
                    "SentiBot",
                    "SenutoBot",
                    "SeoCherryBot",
                    "SeoSiteCheckup",
                    "SeobilityBot",
                    "Seomoz",
                    "Shodan",
                    "Siphon",
                    "SiteCheckerBotCrawler",
                    "SiteExplorer",
                    "SiteLockSpider",
                    "SiteSnagger",
                    "SiteSucker",
                    "Site Sucker",
                    "Sitebeam",
                    "Siteimprove",
                    "Sitevigil",
                    "SlySearch",
                    "SmartDownload",
                    "Snake",
                    "Snapbot",
                    "Snoopy",
                    "SocialRankIOBot",
                    "Sociscraper",
                    "Sogou web spider",
                    "Sosospider",
                    "Sottopop",
                    "SpaceBison",
                    "Spammen",
                    "SpankBot",
                    "Spanner",
                    "Spbot",
                    "Spider_Bot",
                    "Spider_Bot/3.0",
                    "Spinn3r",
                    "SputnikBot",
                    "Sqlmap",
                    "Sqlworm",
                    "Sqworm",
                    "Steeler",
                    "Stripper",
                    "Sucker",
                    "Sucuri",
                    "SuperBot",
                    "SuperHTTP",
                    "Surfbot",
                    "SurveyBot",
                    "Suzuran",
                    "Swiftbot",
                    "Szukacz",
                    "T0PHackTeam",
                    "T8Abot",
                    "Teleport",
                    "TeleportPro",
                    "Telesoft",
                    "Telesphoreo",
                    "Telesphorep",
                    "TheNomad",
                    "The Intraformant",
                    "Thumbor",
                    "TightTwatBot",
                    "TinyTestBot",
                    "Titan",
                    "Toata",
                    "Toweyabot",
                    "Tracemyfile",
                    "Trendiction",
                    "Trendictionbot",
                    "True_Robot",
                    "Turingos",
                    "Turnitin",
                    "TurnitinBot",
                    "TwengaBot",
                    "Twice",
                    "Typhoeus",
                    "URLy.Warning",
                    "URLy Warning",
                    "UnisterBot",
                    "Upflow",
                    "V-BOT",
                    "VB Project",
                    "VCI",
                    "Vacuum",
                    "Vagabondo",
                    "VelenPublicWebCrawler",
                    "VeriCiteCrawler",
                    "VidibleScraper",
                    "Virusdie",
                    "VoidEYE",
                    "Voil",
                    "Voltron",
                    "WASALive-Bot",
                    "WBSearchBot",
                    "WEBDAV",
                    "WISENutbot",
                    "WPScan",
                    "WWW-Collector-E",
                    "WWW-Mechanize",
                    "WWW::Mechanize",
                    "WWWOFFLE",
                    "Wallpapers",
                    "Wallpapers/3.0",
                    "WallpapersHD",
                    "WeSEE",
                    "WebAuto",
                    "WebBandit",
                    "WebCollage",
                    "WebCopier",
                    "WebEnhancer",
                    "WebFetch",
                    "WebFuck",
                    "WebGo IS",
                    "WebImageCollector",
                    "WebLeacher",
                    "WebPix",
                    "WebReaper",
                    "WebSauger",
                    "WebStripper",
                    "WebSucker",
                    "WebWhacker",
                    "WebZIP",
                    "Web Auto",
                    "Web Collage",
                    "Web Enhancer",
                    "Web Fetch",
                    "Web Fuck",
                    "Web Pix",
                    "Web Sauger",
                    "Web Sucker",
                    "Webalta",
                    "WebmasterWorldForumBot",
                    "Webshag",
                    "WebsiteExtractor",
                    "WebsiteQuester",
                    "Website Quester",
                    "Webster",
                    "Whack",
                    "Whacker",
                    "Whatweb",
                    "Who.is Bot",
                    "Widow",
                    "WinHTTrack",
                    "WiseGuys Robot",
                    "Wonderbot",
                    "Woobot",
                    "Wotbox",
                    "Wprecon",
                    "Xaldon WebSpider",
                    "Xaldon_WebSpider",
                    "Xenu",
                    "YaK",
                    "YoudaoBot",
                    "Zade",
                    "Zauba",
                    "Zermelo",
                    "Zeus",
                    "Zitebot",
                    "ZmEu",
                    "ZoomBot",
                    "ZoominfoBot",
                    "ZumBot",
                    "ZyBorg",
                    "adscanner",
                    "anthropic-ai",
                    "archive.org_bot",
                    "arquivo-web-crawler",
                    "arquivo.pt",
                    "autoemailspider",
                    "awario.com",
                    "backlink-check",
                    "cah.io.community",
                    "check1.exe",
                    "clark-crawler",
                    "coccocbot",
                    "cognitiveseo",
                    "cohere-ai",
                    "com.plumanalytics",
                    "crawl.sogou.com",
                    "crawler.feedback",
                    "crawler4j",
                    "dataforseo.com",
                    "dataforseobot",
                    "demandbase-bot",
                    "domainsproject.org",
                    "eCatch",
                    "evc-batch",
                    "everyfeed-spider",
                    "facebookscraper",
                    "gopher",
                    "heritrix",
                    "imagesift.com",
                    "instabid",
                    "internetVista monitor",
                    "ips-agent",
                    "isitwp.com",
                    "iubenda-radar",
                    "linkdexbot",
                    "linkfluence",
                    "lwp-request",
                    "lwp-trivial",
                    "magpie-crawler",
                    "meanpathbot",
                    "mediawords",
                    "muhstik-scan",
                    "netEstate NE Crawler",
                    "oBot",
                    "omgili",
                    "openai",
                    "openai.com",
                    "page scorer",
                    "pcBrowser",
                    "plumanalytics",
                    "polaris version",
                    "probe-image-size",
                    "ripz",
                    "s1z.ru",
                    "satoristudio.net",
                    "scalaj-http",
                    "scan.lol",
                    "seobility",
                    "seocompany.store",
                    "seoscanners",
                    "seostar",
                    "serpstatbot",
                    "sexsearcher",
                    "sitechecker.pro",
                    "siteripz",
                    "sogouspider",
                    "sp_auditbot",
                    "spyfu",
                    "sysscan",
                    "tAkeOut",
                    "trendiction.com",
                    "trendiction.de",
                    "ubermetrics-technologies.com",
                    "voyagerx.com",
                    "webgains-bot",
                    "webmeup-crawler",
                    "webpros.com",
                    "webprosbot",
                    "x09Mozilla",
                    "x22Mozilla",
                    "xpymep1.exe",
                    "zauba.io",
                    "zgrab"
            );
            LocalDateTime now = LocalDateTime.now();

            for (String userAgentName : userAgentNames) {
                UserAgentEntity userAgent = new UserAgentEntity(userAgentName,now, now);
                userAgentRepository.save(userAgent);
            }
        }


        if (transactionTypeRepository.count() == 0) {
            List<String> transactionNames = Arrays.asList(
                    "login",
                    "transfer",
                    "activation",
                    "payment",
                    "purchase",
                    "change pin",
                    "reset pin"
            );

            LocalDateTime now = LocalDateTime.now();

            for (String name : transactionNames) {
                EntityTransactionType transaction = new EntityTransactionType(name, now, now);
                transactionTypeRepository.save(transaction);
            }

            logger.info("Transactions seeded successfully!");
        } else {
            logger.info("Transactions already exist. No seeding required.");
        }
    }
}
