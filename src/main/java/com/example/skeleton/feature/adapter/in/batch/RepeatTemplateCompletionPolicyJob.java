package com.example.skeleton.feature.adapter.in.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.batch.repeat.policy.TimeoutTerminationPolicy;
import org.springframework.batch.repeat.support.RepeatTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RepeatTemplate
 * - SimpleCompletionPolicy : 정의한 chunk size 까지 콜백을 반복한다.
 * - TimeoutTerminationPolicy : 정의한 시간 까지 콜백을 반복한다. (ms)
 * 응답값이 CONTINUABLE 일 경우에만 chunk size 만큼 반복하고, FINISHED 해버리면 즉시 종료한다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RepeatTemplateCompletionPolicyJob {

    public static final String BEAN_NAME = "REPEAT_TEMPLATE_COMPLETION_POLICY_JOB";
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean(BEAN_NAME)
    public Job job() {
        return jobBuilderFactory.get(BEAN_NAME)
                .start(simpleCompletionPolicyStep())
                .next(timeoutTerminationPolicyStep())
                .build();
    }

    private Step simpleCompletionPolicyStep() {
        return stepBuilderFactory.get("SIMPLE_COMPLETION_POLICY_STEP")
                .tasklet((contribution, chunkContext) -> {

                    log.debug("***** simpleCompletionPolicyStep");

                    RepeatTemplate repeatTemplate = new RepeatTemplate();
                    repeatTemplate.setCompletionPolicy(new SimpleCompletionPolicy(3));

                    repeatTemplate.iterate(context -> {
                        log.info("***** repeat, chunk count {}", context.getStartedCount());
                        return RepeatStatus.CONTINUABLE;
                    });

                    return RepeatStatus.FINISHED;

                })
                .build();
    }

    private Step timeoutTerminationPolicyStep() {
        return stepBuilderFactory.get("TIMEOUT_TERMINATION_POLICY_STEP")
                .tasklet((contribution, chunkContext) -> {

                    log.debug("***** timeoutTerminationPolicyStep");

                    RepeatTemplate repeatTemplate = new RepeatTemplate();
                    repeatTemplate.setCompletionPolicy(new TimeoutTerminationPolicy(1));

                    repeatTemplate.iterate(context -> {
                        log.info("***** repeat, time count {}", context.getStartedCount());
                        return RepeatStatus.CONTINUABLE;
                    });

                    return RepeatStatus.FINISHED;

                })
                .build();
    }
}
