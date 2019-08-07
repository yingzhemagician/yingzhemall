python client api connecting to carla unreal for lot ml simulation

### Run carla server
cd to this path and run the world(on Desktop with Display)
'''
$ cd ~/workspaces/infermotion_team_repos/infermotion_carla/carla/Unreal/CarlaUE4/Binaries/Linux
$ ./CarlaUE4 -carla-port=2000
'''
The world window will pop up on the screen

###Change the weather using Python example file
'''
$ cd ~/workspaces/infermotion_team_repos/infermotion_carla/carla/PythonAPI/examples
$ python3.6 dynamic_weather.py
'''

###Run the bbox_generation.py
'''
$ cd ~/workspaces/infermotion_team_repos/infermotion_lot_ml_sim
$ python3.6 bbox_generation.py
'''
The while loop will run for 10 seconds. Four cameras will take and save images every 4 seconds.
In the while loop there is a save_pascal function so the Writer will find the image first and get the vehicles information then write them to json.

###Used profiling tool (pytracing) to visualize the time cost
A trace.out file will be created and saved in out folder. Use Chrome's tracing to open that

###Pascal Voc to JSON writer and templates are customized
'''
$ cd ~/workspaces/infermotion_team_repos/infermotion_lot_ml_sim/lib
'''
pascal_voc_writer.py
