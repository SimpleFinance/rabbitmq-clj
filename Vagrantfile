#!/usr/bin/env ruby

Vagrant.configure('2') do |config|
  config.vm.hostname = 'rabbitmqclj'
  config.vm.box = ENV['VAGRANT_BOX']
  config.vm.network :forwarded_port, guest: 5671,  host: 5671
  config.vm.network :forwarded_port, guest: 5672,  host: 5672
  config.vm.network :forwarded_port, guest: 15672, host: 15672
end
