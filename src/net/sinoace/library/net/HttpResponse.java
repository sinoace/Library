package net.sinoace.library.net;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import net.sinoace.library.io.IOUtils;

/**
 * Http响应
 */
public class HttpResponse {
	private HttpURLConnection httpURLConn;
	private XCache xCache;
	private Vary vary;
	private SetCookie setCookie;
	private Server server;
	private Location location;
	private P3P p3p;
	private LastModified lastModified;
	private Expires expires;
	private Date date;
	private ContentType contentType;
	private ContentLength contentLength;
	private ContentEncoding contentEncoding;
	private ContentDisposition contentDisposition;
	private Connection connection;
	private CacheControl cacheControl;
	private AcceptRanges acceptRanges;
	
	public HttpResponse(HttpURLConnection httpURLConn){
        this.httpURLConn = httpURLConn;
	}
	
	/**
	 * 获取输入流
	 * @return 输入流
	 * @throws IOException
	 */
	public BufferedInputStream getInputStream() throws IOException{
		return new BufferedInputStream(httpURLConn.getInputStream());
	}
	
	/**
	 * 获取返回的数据
	 * @return 返回的数据
	 * @throws IOException
	 */
	public byte[] getData() throws IOException{
		byte[] responseData = null;
		BufferedInputStream bis = getInputStream();
		try {
			responseData = IOUtils.read(bis);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}finally{
			if(bis != null){
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return responseData;
	}
	
	/**
	 * 将返回的内容转换成一个字符串 
	 * @return 获取返回的内容
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	public String getString() throws IOException{
		return new String(getData(), getContentType().getCharset(Charset.defaultCharset().name()));
	}
	
	/**
	 * 获取状态码
	 * @return 状态码
	 * @throws IOException
	 */
	public int getCode() throws IOException{
		return httpURLConn.getResponseCode();
	}
	
	/**
	 * 根据给定的KEY获取头字段
	 * @param key 给定的KEY
	 * @return 头字段
	 */
	public String getHeaderField(String key){
		return httpURLConn.getHeaderField(key);
	}
	
	/**
	 * 获取所有的头字段
	 * @return 所有的头字段
	 */
	public Map<String, List<String>> getHeaderFields(){
		return httpURLConn.getHeaderFields();
	}
	
	/**
	 * 获取消息
	 * @return 消息
	 * @throws IOException
	 */
	public String getMessage() throws IOException{
		return httpURLConn.getResponseMessage();
	}
	
	/**
	 * 消息是OK
	 * @return
	 * @throws IOException
	 */
	public boolean messageIsOk() throws IOException{
		return "ok".equalsIgnoreCase(getMessage());
	}
	
	/**
	 * 消息是Partial Content，部分内容当请求头中包含Range属性时响应的消息标记就是Partial Content
	 * @return
	 * @throws IOException
	 */
	public boolean messageIsPartialContent() throws IOException{
		return "Partial Content".equalsIgnoreCase(getMessage());
	}
	
	/**
	 * 状态码是200
	 * @return
	 * @throws IOException
	 */
	public boolean codeIs200() throws IOException{
		return 200 == getCode();
	}
	
	/**
	 * 状态码是206，当请求头中包含Range属性时响应的状态码就是206，多用于多线程下载或者断线续传
	 * @return
	 * @throws IOException
	 */
	public boolean codeIs206() throws IOException{
		return 206 == getCode();
	}
	
	public XCache getXCache(){
		if(xCache == null){
			xCache = new XCache(getHeaderField(XCache.NAME));
		}
		return xCache;
	}
	
	public Vary getVary(){
		if(vary == null){
			vary = new Vary(getHeaderField(Vary.NAME));
		}
		return vary;
	}
	
	public SetCookie getSetCookie(){
		if(setCookie == null){
			setCookie = new SetCookie(getHeaderField(SetCookie.NAME));
		}
		return setCookie;
	}
	
	public Server getServer(){
		if(server == null){
			server = new Server(getHeaderField(Server.NAME)); 
		}
		return server;
	}
	
	public Location getLocation(){
		if(location == null){
			location = new Location(getHeaderField(Location.NAME));
		}
		return location;
	}
	
	public P3P getP3P(){
		if(p3p == null){
			p3p = new P3P(getHeaderField(P3P.NAME));
		}
		return p3p;
	}
	
	public LastModified getLastModified(){
		if(lastModified == null){
			lastModified = new LastModified(getHeaderField(LastModified.NAME));
		}
		return lastModified;
	}
	
	public Expires getExpires(){
		if(expires == null){
			expires = new Expires(getHeaderField(Expires.NAME));
		}
		return expires;
	}
	
	public Date getDate(){
		if(date == null){
			date = new Date(getHeaderField(Date.NAME));
		}
		return date;
	}
	
	public ContentType getContentType(){
		if(contentType == null){
			contentType = new ContentType(getHeaderField(ContentType.NAME));
		}
		return contentType;
	}
	
	public ContentLength getContentLength(){
		if(contentLength == null){
			contentLength = new ContentLength(getHeaderField(ContentLength.NAME));
		}
		return contentLength;
	}
	
	public ContentEncoding getContentEncoding(){
		if(contentEncoding == null){
			contentEncoding = new ContentEncoding(getHeaderField(ContentEncoding.NAME)); 
		}
		return new ContentEncoding(getHeaderField(ContentEncoding.NAME));
	}
	
	public ContentDisposition getContentDisposition(){
		if(contentDisposition == null){
			contentDisposition = new ContentDisposition(getHeaderField(ContentDisposition.NAME));
		}
		return contentDisposition;
	}
	
	public Connection getConnection(){
		if(connection == null){
			connection = new Connection(getHeaderField(Connection.NAME));
		}
		return connection;
	}
	
	public CacheControl getCacheControl(){
		if(cacheControl == null){
			cacheControl = new CacheControl(getHeaderField(CacheControl.NAME));
		}
		return cacheControl;
	}
	
	public AcceptRanges getAcceptRanges(){
		if(acceptRanges == null){
			acceptRanges = new AcceptRanges(getHeaderField(AcceptRanges.NAME));
		}
		return acceptRanges;
	}
	
	/**
	 * 获取Http连接对象
	 * @return Http连接对象
	 */
	public HttpURLConnection getHttpURLConn() {
		return httpURLConn;
	}

	/**
	 * 设置Http连接对象
	 * @param httpURLConn Http连接对象
	 */
	public void setHttpURLConn(HttpURLConnection httpURLConn) {
		this.httpURLConn = httpURLConn;
	}
}
