package priv.zxy.moonstep.DAO.constant;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/5
 * 描述: 服务器基类，用来存储连接服务器的地址
 **/

public class UrlBase {

    public static final String START_PAGE_URL = "http://120.79.154.236:8080/start/";

    public static final String CHANGE_PASSWORD_SERVLET_URL = "http://120.79.154.236:8080/MoonStep/ChangePasswordServlet";

    public static final String CHECK_USER_ID_SERVLET_URL = "http://120.79.154.236:8080/MoonStep/CheckUserIDServlet";

    public static final String CHECK_PHONE_NUMBER_IN_SERVLET_URL = "http://120.79.154.236:8080/MoonStep/CheckPhoneServlet";

    public static final String REGISTER_SERVLET_URL = "http://120.79.154.236:8080/MoonStep/RegisterServlet";

    public static final String LOGIN_SERVLET_URL = "http://120.79.154.236:8080/MoonStep/LoginServlet";

    public static final String GET_PET_INFO_SERVLET_URL = "http://120.79.154.236:8080/MoonStep/GetPetInfoServlet";

    public static final String LOCATION_SERVLET_URL = "http://120.79.154.236:8080/MoonStep/LocationServlet";

    public static final String USER_GOOD_URL = "http://120.79.154.236:8080/MoonStep/GetUserGoodsServlet";

    public static final String GOOD_TREASURE = "http://120.79.154.236:8080/MoonStep/GetTreasureServlet";

    // 服务器URL路径（配合Retrofit使用）
    public static final String BASE_URL = "http://120.79.154.236:8080/";

    // 用户种族信息相对URL路径
    public static final String GET_RACE_INFO_SERVLET_URL = "MoonStep/GetRaceInfoServlet";

    /**
     * 图片上传路径
     */
    public static final String PULL_IMAGE_TO_SERVER_URL = "http://120.79.154.236:8080/MoonStep/PullUserImageServlet";

    /**
     * 用户网络头像路径
     */
    public static final String USER_HEAD_BASE_PATH = "http://120.79.154.236:8080/MoonStep/head/";
}
