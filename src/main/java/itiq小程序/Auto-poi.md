itiq 小程序项目

# 1. Auto-poi 依赖

```xml
<!-- AutoPoi Excel工具类-->
<dependency>
    <groupId>org.jeecgframework</groupId>
    <artifactId>autopoi-web</artifactId>
    <version>1.2</version>
    <exclusions>
        <exclusion>
            <groupId>commons-codec</groupId>
            <artifactId>commons-codec</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

# 2. Auto-poi 的基本注解

* `@Excel`: 导出基本注解
* `@ExcelCollection`: 导出集合注解(适用于导出一对多的情况)

`@Excel`注解属性：
```java
/**
 * Excel 导出基本注释
 * 
 * @author JEECG
 * @date 2014年6月20日 下午10:25:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel {

	/**
	 * 导出时间设置,如果字段是Date类型则不需要设置 数据库如果是string 类型,这个需要设置这个数据库格式
	 */
	public String databaseFormat() default "yyyyMMddHHmmss";

	/**
	 * 导出的时间格式,以这个是否为空来判断是否需要格式化日期
	 */
	public String exportFormat() default "";

	/**
	 * 时间格式,相当于同时设置了exportFormat 和 importFormat
	 */
	public String format() default "";

	/**
	 * 导出时在excel中每个列的高度 单位为字符，一个汉字=2个字符
	 */
	public double height() default 10;

	/**
	 * 导出类型 1 从file读取_old ,2 是从数据库中读取字节文件, 3文件地址_new, 4网络地址 同样导入也是一样的
	 *
	 */
	public int imageType() default 3;

	/**
	 * 导入的时间格式,以这个是否为空来判断是否需要格式化日期
	 */
	public String importFormat() default "";

	/**
	 * 文字后缀,如% 90 变成90%
	 */
	public String suffix() default "";

	/**
	 * 是否换行 即支持\n
	 */
	public boolean isWrap() default true;

	/**
	 * 合并单元格依赖关系,比如第二列合并是基于第一列 则{1}就可以了
	 */
	public int[] mergeRely() default {};

	/**
	 * 纵向合并内容相同的单元格
	 */
	public boolean mergeVertical() default false;

	/**
	 * 导出时，对应数据库的字段 主要是用户区分每个字段， 不能有annocation重名的 导出时的列名
	 * 导出排序跟定义了annotation的字段的顺序有关 可以使用a_id,b_id来确实是否使用
	 */
	public String name();

	/**
	 * 是否需要纵向合并单元格(用于含有list中,单个的单元格,合并list创建的多个row)
	 */
	public boolean needMerge() default false;

	/**
	 * 展示到第几个可以使用a_id,b_id来确定不同排序
	 */
	public String orderNum() default "0";

	/**
	 * 值得替换 导出是{"男_1","女_0"} 导入反过来,所以只用写一个
	 */
	public String[] replace() default {};
	
	/**
	 * 导入路径,如果是图片可以填写,默认是upload/className/ IconEntity这个类对应的就是upload/Icon/
	 *
	 */
	public String savePath() default "upload";

	/**
	 * 导出类型 1 是文本 2 是图片,3是函数,4是数字 默认是文本
	 */
	public int type() default 1;

	/**
	 * 导出时在excel中每个列的宽 单位为字符，一个汉字=2个字符 如 以列名列内容中较合适的长度 例如姓名列6 【姓名一般三个字】
	 * 性别列4【男女占1，但是列标题两个汉字】 限制1-255
	 */
	public double width() default 10;

	/**
	 * 是否自动统计数据,如果是统计,true的话在最后追加一行统计,把所有数据都和 这个处理会吞没异常,请注意这一点
	 * 
	 * @return
	 */
	public boolean isStatistics() default false;
	
	/**
	 * 方法描述: 数据字典表
	 * 作    者： yiming.zhang
	 * 日    期： 2014年5月11日-下午5:26:40
	 * @return 
	 * 返回类型： String
	 */
	public String dictTable() default "";

	/**
	 * 方法描述:  数据code
	 * 作    者： yiming.zhang
	 * 日    期： 2014年5月13日-下午9:37:16
	 * @return 
	 * 返回类型： String
	 */
	public String dicCode() default "";
	
	/**
	 * 方法描述:  数据Text
	 * 作    者： yiming.zhang
	 * 日    期： 2014年5月11日-下午5:29:05
	 * @return 
	 * 返回类型： String
	 */
	public String dicText() default "";
	
	/**
	 * 导入数据是否需要转化  
	 * 若是为true,则需要在pojo中加入 方法：convertset字段名(String text)  
	 * @return
	 */
	public boolean importConvert() default false;
	/**
	 * 导出数据是否需要转化
	 * 若是为true,则需要在pojo中加入方法:convertget字段名()
	 * @return
	 */
	public boolean exportConvert() default false;
	
	/**
	 * 值的替换是否支持替换多个(默认true,若数据库值本来就包含逗号则需要配置该值为false)
	 * @author taoYan
	 * @since 2018年8月1日
	 */
	public boolean multiReplace() default true;

	/**
	 * 父表头
	 * @return
	 */
	String groupName() default "";

	/**
	 * 数字格式化,参数是Pattern,使用的对象是DecimalFormat
	 * @return
	 */
	String numFormat() default "";
}
```

`@ExcelCollection`注解属性
```java
/**
 * 导出的集合
 * 
 * @author JEECG 2013年8月24日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelCollection {

	/**
	 * 定义excel导出ID 来限定导出字段,处理一个类对应多个不同名称的情况
	 */
	public String id() default "";

	/**
	 * 导出时，对应数据库的字段 主要是用户区分每个字段， 不能有annocation重名的 导出时的列名
	 * 导出排序跟定义了annotation的字段的顺序有关 可以使用a_id,b_id来确实是否使用
	 */
	public String name();

	/**
	 * 展示到第几个同样可以使用a_id,b_id
	 * 
	 */
	public String orderNum() default "0";

	/**
	 * 创建时创建的类型 默认值是 arrayList
	 */
	public Class<?> type() default ArrayList.class;
}
```

# 3. 导入导出工具类

## 3.1 导入工具类
```java
public class ImportExcelUtil {

    public static Result<?> automationImporReturnRes(int errorLines, int successLines, int updateLines,
                                                     List<String> errorMessage, List<String> updateMessage) throws IOException {
        if (errorLines == 0) {
            String test = String.join(",",errorMessage);
            return Result.ok("共" + successLines + "行数据导入成功！共"+updateLines+"行数据更新成功！"+String.join(",",errorMessage));
        } else {
            JSONObject result = new JSONObject(5);
            int totalCount = successLines + updateLines + errorLines;
            result.put("totalCount", totalCount);
            result.put("errorCount", errorLines);
            result.put("successCount", successLines);
            result.put("msg", "总上传行数：" + totalCount + "，已导入行数：" + successLines + "，已更新行数："+ updateLines +"，错误行数：" + errorLines+"。");
            result.put("detail", String.join(",",errorMessage));
            String fileUrl = PmsUtil.saveErrorTxtByList(errorMessage, "userImportExcelErrorLog");
            int lastIndex = fileUrl.lastIndexOf(File.separator);
            String fileName = fileUrl.substring(lastIndex + 1);
//            result.put("fileUrl", "/sys/common/static/" + fileUrl);
//            result.put("fileName", fileName);
            Result res = Result.ok(result);
            res.setCode(201);
            res.setMessage("文件导入成功，但有错误。");
            return res;
        }
    }

    public static Result<?> imporReturnRes(int errorLines, int successLines, List<String> errorMessage) throws IOException {
        if (errorLines == 0) {
            return Result.ok("共" + successLines + "行数据全部导入成功！");
        } else {
            JSONObject result = new JSONObject(5);
            int totalCount = successLines + errorLines;
            result.put("totalCount", totalCount);
            result.put("errorCount", errorLines);
            result.put("successCount", successLines);
            result.put("msg", "总上传行数：" + totalCount + "，已导入行数：" + successLines + "，错误行数：" + errorLines);
            String fileUrl = PmsUtil.saveErrorTxtByList(errorMessage, "userImportExcelErrorLog");
            int lastIndex = fileUrl.lastIndexOf(File.separator);
            String fileName = fileUrl.substring(lastIndex + 1);
            result.put("fileUrl", "/sys/common/static/" + fileUrl);
            result.put("fileName", fileName);
            Result res = Result.ok(result);
            res.setCode(201);
            res.setMessage("文件导入成功，但有错误。");
            return res;
        }
    }

    public static List<String> importDateSave(List<Object> list, Class serviceClass,List<String> errorMessage,String errorFlag)  {
        IService bean =(IService) SpringContextUtils.getBean(serviceClass);
        for (int i = 0; i < list.size(); i++) {
            try {
                boolean save = bean.save(list.get(i));
                if(!save){
                    throw new Exception(errorFlag);
                }
            } catch (Exception e) {
                String message = e.getMessage();
                int lineNumber = i + 1;
                // 通过索引名判断出错信息
                if (message.contains(CommonConstant.SQL_INDEX_UNIQ_MATERIAL_EPIROC_PART_NUMBER)) {
                    errorMessage.add("第 " + lineNumber + " 行：零件号已经存在，忽略导入。");
                } else if (message.contains(CommonConstant.SQL_INDEX_UNIQ_JOB_CLASS_NAME)) {
                    errorMessage.add("第 " + lineNumber + " 行：任务类名已经存在，忽略导入。");
                }else if (message.contains(CommonConstant.SQL_INDEX_UNIQ_CODE)) {
                    errorMessage.add("第 " + lineNumber + " 行：职务编码已经存在，忽略导入。");
                }else if (message.contains(CommonConstant.SQL_INDEX_UNIQ_DEPART_ORG_CODE)) {
                    errorMessage.add("第 " + lineNumber + " 行：部门编码已经存在，忽略导入。");
                }else {
                    errorMessage.add("第 " + lineNumber + " 行：未知错误，忽略导入");
                    log.error(e.getMessage(), e);
                }
            }
        }
        return errorMessage;
    }

    public static List<String> importDataSave(List<Object> list, Class serviceClass,List<String> errorMessage,String errorFlag)  {
        IService bean =(IService) SpringContextUtils.getBean(serviceClass);
        for (int i = 0; i < list.size(); i++) {
            try {
                boolean save = bean.save(list.get(i));
                if(!save){
                    throw new Exception(errorFlag);
                }
            } catch (Exception e) {
                String message = e.getMessage();
                int lineNumber = i + 1;
                // 通过索引名判断出错信息
                if (message.contains(CommonConstant.SQL_INDEX_UNIQ_MATERIAL_EPIROC_PART_NUMBER)) {
                    errorMessage.add("第 " + lineNumber + " 行：零件号已经存在，忽略导入。");
                } else if (message.contains(CommonConstant.SQL_INDEX_UNIQ_JOB_CLASS_NAME)) {
                    errorMessage.add("第 " + lineNumber + " 行：任务类名已经存在，忽略导入。");
                }else if (message.contains(CommonConstant.SQL_INDEX_UNIQ_CODE)) {
                    errorMessage.add("第 " + lineNumber + " 行：职务编码已经存在，忽略导入。");
                }else if (message.contains(CommonConstant.SQL_INDEX_UNIQ_DEPART_ORG_CODE)) {
                    errorMessage.add("第 " + lineNumber + " 行：部门编码已经存在，忽略导入。");
                }else {
                    errorMessage.add("第 " + lineNumber + " 行：未知错误，忽略导入");
                    log.error(e.getMessage(), e);
                }
            }
        }
        return errorMessage;
    }

    public static List<String> importDateSaveOne(Object obj, Class serviceClass,List<String> errorMessage,int i,String errorFlag)  {
        IService bean =(IService) SpringContextUtils.getBean(serviceClass);
        try {
            boolean save = bean.save(obj);
            if(!save){
                throw new Exception(errorFlag);
            }
        } catch (Exception e) {
            String message = e.getMessage();
            int lineNumber = i + 1;
            // 通过索引名判断出错信息
            if (message.contains(CommonConstant.SQL_INDEX_UNIQ_SYS_ROLE_CODE)) {
                errorMessage.add("第 " + lineNumber + " 行：角色编码已经存在，忽略导入。");
            } else if (message.contains(CommonConstant.SQL_INDEX_UNIQ_JOB_CLASS_NAME)) {
                errorMessage.add("第 " + lineNumber + " 行：任务类名已经存在，忽略导入。");
            }else if (message.contains(CommonConstant.SQL_INDEX_UNIQ_CODE)) {
                errorMessage.add("第 " + lineNumber + " 行：职务编码已经存在，忽略导入。");
            }else if (message.contains(CommonConstant.SQL_INDEX_UNIQ_DEPART_ORG_CODE)) {
                errorMessage.add("第 " + lineNumber + " 行：部门编码已经存在，忽略导入。");
            }else {
                errorMessage.add("第 " + lineNumber + " 行：未知错误，忽略导入");
                log.error(e.getMessage(), e);
            }
        }
        return errorMessage;
    }

}
```

## 3.2 导出工具类
```java
public class ExportUtil {
    
    /**
     * 导出excel
     * @param pageList 数据列表
     * @param selections 导出指定字段,逗号隔开
     * @param clazz 数据实体类class
     * @param sheetName 报表sheet名称
     * @param fileName 报表文件名称
     * @return
     */
    public ModelAndView exportXlsEx(List<T> pageList, String selections, Class<T> clazz, String sheetName, String fileName) {
        List<T> exportList = null;
        // 过滤选中数据
        if (oConvertUtils.isNotEmpty(selections)) {
            List<String> selectionList = Arrays.asList(selections.split(","));
            exportList = pageList.stream().filter(item -> selectionList.contains(getId(item))).collect(Collectors.toList());
        } else {
            exportList = pageList;
        }

        //AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, fileName); // 此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.CLASS, clazz);
        ExportParams params = new ExportParams();
        params.setSheetName(sheetName);
        params.setType(ExcelType.XSSF);
        // 设置序号自增
//        params.setAddIndex(true); // 此设置会导致一对多导出时出现bug
        mv.addObject(NormalExcelConstants.PARAMS, params);
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        if(StringUtils.isNotEmpty(selections)){
            mv.addObject(NormalExcelConstants.EXPORT_FIELDS, selections);// 导出指定字段
        }
        return mv;
    }

    /**
     * 多 sheet 导出
     * @param fileName 导出文件名
     * @param sheetsList 多sheet数据以及参数列表
     * @return
     */
    public ModelAndView exportMultiSheet(String fileName, List<Map<String, Object>> sheetsList) {
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<Map<String, Object>> infoList = new ArrayList<>();

        for (Map<String, Object> map: sheetsList) {
            Map<String, Object> temp = new HashMap<>();
            ExportParams exportParams = new ExportParams();
            exportParams.setSheetName((String) map.get("sheetName"));
            exportParams.setType(ExcelType.XSSF);
            temp.put(NormalExcelConstants.PARAMS, exportParams);
            temp.put(NormalExcelConstants.CLASS, map.get("class"));
            temp.put(NormalExcelConstants.DATA_LIST, map.get("data"));
            infoList.add(temp);
        }

        // 设置excel参数
        mv.addObject(NormalExcelConstants.FILE_NAME, fileName);
        mv.addObject(NormalExcelConstants.MAP_LIST, infoList);
        return mv;
    }
}
```

# 4. 导入导出实例

## 4.1 import example
### 单 Sheet 导入
```java
    /**
     * 物料管理导入(6.21update)
     * @param request 请求
     * @param response 响应
     * @return
     */
    @ApiOperation(value = "物料导入")
    @PostMapping("/material")
    public Result<?> importMaterial(HttpServletRequest request, HttpServletResponse response){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setNeedSave(true);
            try {
                return importService.importMaterial(file, params, request.getParameter("username"));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return Result.error("文件导入失败！");
    }
```
```java
    /**
     * 物料管理导入(6.21update)
     * @param file 上传的文件
     * @param params 导入参数
     * @param username 用户名
     * @return
     * @throws Exception
     */
    @Override
    public Result importMaterial(MultipartFile file, ImportParams params, String username) throws Exception {
        List<Object> materialList = ExcelImportUtil.importExcel(file.getInputStream(), SysMateriel.class, params);
        int total = materialList.size();
        List<String> errorStrs = new ArrayList<>();
        List<String> updateStrs = new ArrayList<>();
        Integer errorLines = 0;
        Integer updateLines = 0;
        Integer successLines = 0;

        for (int i = 0; i < materialList.size(); i++) {
            try {
                SysMateriel sysMateriel = (SysMateriel)materialList.get(i);
                // 根据可用机型文本查询设置可用机型码
                String modelText = sysMateriel.getModelText();
                if(!StringUtils.isEmpty(modelText)){
                    List<String> list = Arrays.asList(modelText.split(","));
                    List<String> modelValues = sysStockMapper.selectModelValue(list.stream()
        .map(s -> "\'" + s + "\'").collect(Collectors.joining(", ")));
                    sysMateriel.setModelValue(String.join(",", modelValues));
                }
                // 根据零件类型文本查询设置零件类型代码
                String partType = sysMaterialMapper.selectPartType(sysMateriel.getPartType());
                sysMateriel.setPartType(partType);
                // 根据id判断更新or插入
                String materialId = sysMateriel.getId();
                if (materialId != null && materialId != "") { // 执行更新
                    updateStrs.add("第 " + (i + 1) + " 行：数据已经存在，忽略导入，进行更新。");
                    sysMateriel.setUpdateBy(username);
                    sysMateriel.setUpdateTime(DateUtils.getDate());
                    sysMaterialMapper.updateById(sysMateriel);
                } else { // 执行插入
                    sysMateriel.setCreateBy(username);
                    sysMateriel.setCreateTime(DateUtils.getDate());
                    sysMateriel.setUpdateBy(username);
                    sysMateriel.setUpdateTime(DateUtils.getDate());
                    sysMaterialMapper.insert(sysMateriel);
                }
            } catch (Exception e) {
                errorStrs.add("第 " + (i + 1) + " 行：未知错误，忽略导入");
                log.error(e.getMessage(), e);
            }
        }
        // 错误数量
        errorLines += errorStrs.size();
        // 更新数量
        updateLines += updateStrs.size();
        successLines = (total - errorLines - updateLines);
        return ImportExcelUtil.automationImporReturnRes(errorLines, successLines, updateLines, errorStrs, updateStrs);
    }
```

### 多 Sheet 导入
```java
/**
     * 库存管理导入(6.21update)
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "库存导入")
    @PostMapping("/stock")
    public Result<?> importStock(HttpServletRequest request, HttpServletResponse response){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            try {
                Map<String, Integer> rowMap = new HashMap<>();
                Workbook workbook = new XSSFWorkbook(file.getInputStream());
                ImportParams params = new ImportParams();
                params.setSheetNum(3);
                params.setNeedSave(true);
                for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum ++) {
                    Sheet sheetAt = workbook.getSheetAt(sheetNum);
                    int physicalNumberOfRows = sheetAt.getPhysicalNumberOfRows();
                    rowMap.put("sheetRow" + sheetNum, physicalNumberOfRows - 1);
                }
                System.out.println(rowMap);
                return importService.importStock(file, params, request.getParameter("username"), rowMap);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return Result.error("文件导入失败！");
    }
```
```java
/**
     * 库存管理导入(6.21update)
     * @param file
     * @param params
     * @param username
     * @return
     * @throws Exception
     */
    @Override
    public Result importStock(MultipartFile file, ImportParams params, String username, Map<String, Integer> rowMap) throws Exception {
        List<Object> stockList = ExcelImportUtil.importExcel(file.getInputStream(), SysStock.class, params);
        int total = stockList.size();
        List<String> errorStrs = new ArrayList<>();
        List<String> updateStrs = new ArrayList<>();
        Integer errorLines = 0;
        Integer updateLines = 0;
        Integer successLines = 0;
        // 获取每个 sheet 的数据量
        Integer sheetRow1 = rowMap.get("sheetRow0");
        Integer sheetRow2 = rowMap.get("sheetRow1");
        for (int i = 0; i < stockList.size(); i++) {
            try {
                SysStock sysStock = (SysStock)stockList.get(i);
                // 根据可用机型文本查询设置可用机型码
                String modelText = sysStock.getAvailableModelText();
                if(!StringUtils.isEmpty(modelText)){
                    List<String> list = Arrays.asList(modelText.split(","));
                    List<String> modelValues = sysStockMapper.selectModelValue(list.stream()
        .map(s -> "\'" + s + "\'").collect(Collectors.joining(", ")));
                    sysStock.setAvailableModelValue(String.join(",", modelValues));
                }
                // 根据零件类型文本查询设置零件类型代码
                String partType = sysMaterialMapper.selectPartType(sysStock.getPartType());
                sysStock.setPartType(partType);
                // 根据每个 sheet 的数据量划分仓库
                String storageCode;
                if (i < sheetRow1) {
                    storageCode = "3"; // SAP 系统库存
                } else if (i >= sheetRow1 + sheetRow2) {
                    storageCode = "2"; // 样机车间寄存库
                } else {
                    storageCode = "1"; // 威卡寄存库
                }
                sysStock.setStorage(storageCode);
                // 根据id判断更新or插入
                String stockId = sysStock.getId();
                if (stockId != null && stockId != "") { // 执行更新
                    updateStrs.add("第 " + (i + 1) + " 行：数据已经存在，忽略导入，进行更新。");
                    sysStock.setId(stockId);
                    sysStock.setUpdateBy(username);
                    sysStock.setUpdateTime(DateUtils.getDate());
                    sysStockMapper.updateById(sysStock);
                } else { // 执行插入
                    sysStock.setCreateBy(username);
                    sysStock.setCreateTime(DateUtils.getDate());
                    sysStock.setUpdateBy(username);
                    sysStock.setUpdateTime(DateUtils.getDate());
                    sysStockMapper.insert(sysStock);
                }
            } catch (Exception e) {
                errorStrs.add("第 " + (i + 1) + " 行：未知错误，忽略导入");
                log.error(e.getMessage(), e);
            }
        }
        // 错误数量
        errorLines += errorStrs.size();
        // 更新数量
        updateLines += updateStrs.size();
        successLines += (total - errorLines - updateLines);
        return ImportExcelUtil.automationImporReturnRes(errorLines, successLines, updateLines, errorStrs, updateStrs);
    }
```

## 4.2 export example
### 单 sheet 导出
```java
    /**
     * 物料管理导出(6.21update)
     * @return
     */
    @ApiOperation(value = "物料管理导出")
    @GetMapping("/material")
    public ModelAndView exportMaterial() {
        List<SysMateriel> materielList = sysMaterialMapper.getAllMaterials();
        String selections = "";
        ModelAndView mv = new ExportUtil().exportXlsEx(materielList, selections, SysMateriel.class,
                "物料管理报表", "物料管理报表");    // 写成 new ExportUtil()<> 会报错
        return mv;
    }
```
### 多 sheet 导出
```java
    /**
     * 库存管理导出(6.21update)
     * @return
     */
    @ApiOperation(value = "库存管理导出")
    @GetMapping("/stock")
    public ModelAndView exportStock() {
        Map<String, Object> exportWikaMap = new HashMap<String, Object>();
        Map<String, Object> exportSAPMap = new HashMap<String, Object>();
        Map<String, Object> exportProMap = new HashMap<String, Object>();

        // 组装参数
        exportSAPMap.put("sheetName", "SAP系统库存");
        exportSAPMap.put("class", SysStockForSAPEX.class);
        exportSAPMap.put("data", sysStockMapper.selectSAPStock());
        exportWikaMap.put("sheetName", "威卡寄存库");
        exportWikaMap.put("class", SysStockForWikaEX.class);
        exportWikaMap.put("data", sysStockMapper.selectWikaStock());
        exportProMap.put("sheetName", "样机车间寄存库");
        exportProMap.put("class", SysStockForProEX.class);
        exportProMap.put("data", sysStockMapper.selectProStock());

        List<Map<String, Object>> sheetsList = new ArrayList<Map<String, Object>>();
        sheetsList.add(exportSAPMap);
        sheetsList.add(exportWikaMap);
        sheetsList.add(exportProMap);

        ModelAndView mv = new ExportUtil().exportMultiSheet("库存管理报表", sheetsList);
        return mv;
    }
```

