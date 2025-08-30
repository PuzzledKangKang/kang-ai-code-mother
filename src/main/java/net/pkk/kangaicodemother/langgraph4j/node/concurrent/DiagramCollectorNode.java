package net.pkk.kangaicodemother.langgraph4j.node.concurrent;

import lombok.extern.slf4j.Slf4j;
import net.pkk.kangaicodemother.langgraph4j.model.ImageCollectionPlan;
import net.pkk.kangaicodemother.langgraph4j.model.ImageResource;
import net.pkk.kangaicodemother.langgraph4j.state.WorkflowContext;
import net.pkk.kangaicodemother.langgraph4j.tools.MermaidDiagramTool;
import net.pkk.kangaicodemother.utils.SpringContextUtil;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import java.util.ArrayList;
import java.util.List;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

/**
 * 架构图收集节点
 */
@Slf4j
public class DiagramCollectorNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            List<ImageResource> diagrams = new ArrayList<>();
            try {
                ImageCollectionPlan plan = context.getImageCollectionPlan();
                if (plan != null && plan.getDiagramTasks() != null) {
                    MermaidDiagramTool diagramTool = SpringContextUtil.getBean(MermaidDiagramTool.class);
                    log.info("开始并发生成架构图，任务数: {}", plan.getDiagramTasks().size());
                    for (ImageCollectionPlan.DiagramTask task : plan.getDiagramTasks()) {
                        List<ImageResource> images = diagramTool.generateMermaidDiagram(
                                task.mermaidCode(), task.description());
                        if (images != null) {
                            diagrams.addAll(images);
                        }
                    }
                    log.info("架构图生成完成，共生成 {} 张图片", diagrams.size());
                }
            } catch (Exception e) {
                log.error("架构图生成失败: {}", e.getMessage(), e);
            }
            context.setDiagrams(diagrams);
            context.setCurrentStep("架构图生成");
            return WorkflowContext.saveContext(context);
        });
    }
}
