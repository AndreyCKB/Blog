package com.example.rest.spring.blog.service.topic;

import com.example.rest.spring.blog.models.Topic;
import com.example.rest.spring.blog.repositories.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TopicServiceImpl implements TopicService {

    private TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public <S extends Topic> S save(S topic) {
        boolean existsTopic = this.topicRepository.existsByNameIgnoreCase(topic.getName());
        if (existsTopic){
            return topic;
        } else {
            return this.topicRepository.save(topic);
        }
    }

    @Override
    public Optional<Topic> findById(Long id) {
        return this.topicRepository.findById(id);
    }

    @Override
    public Iterable<Topic> findAll() {
        return this.topicRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        this.topicRepository.deleteById(id);
    }
}
