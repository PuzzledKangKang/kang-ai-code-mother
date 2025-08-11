package net.pkk.kangaicodemother.core.saver;

import cn.hutool.core.util.StrUtil;
import net.pkk.kangaicodemother.ai.model.HtmlCodeResult;
import net.pkk.kangaicodemother.exception.BusinessException;
import net.pkk.kangaicodemother.exception.ErrorCode;
import net.pkk.kangaicodemother.model.enums.CodeGenTypeEnum;

/**
 * @author 林子康
 * @version 1.0
 * @since 2025/8/11 11:42
 */
public class HtmlCodeFileSaverTemplate extends CodeFileSaverTemplate<HtmlCodeResult> {

    @Override
    protected CodeGenTypeEnum getCodeType() {
        return CodeGenTypeEnum.HTML;
    }

    @Override
    protected void saveFiles(HtmlCodeResult result, String baseDirPath) {
        writeToFile(baseDirPath, "index.html", result.getHtmlCode());
    }

    @Override
    protected void validateInput(HtmlCodeResult result) {
        super.validateInput(result);
        // Html 代码不能为空
        if (StrUtil.isBlank(result.getHtmlCode())) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "HTML 代码不能为空");
        }
    }

}
