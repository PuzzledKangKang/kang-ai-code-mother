package net.pkk.kangaicodemother.langgraph4j.config;

/**
 * 可视化调试配置，实际上很难使用
 */
@Configuration
public class LangGraphStudioSampleConfig extends AbstractLangGraphStudioConfig {

    final LangGraphFlow flow;

    public LangGraphStudioSampleConfig() throws GraphStateException {
        var workflow = new CodeGenWorkflow().createWorkflow().stateGraph;
        // define your workflow   
        this.flow = LangGraphFlow.builder()
                .title("LangGraph Studio")
                .stateGraph(workflow)
                .build();
    }

    @Override
    public LangGraphFlow getFlow() {
        return this.flow;
    }
}
