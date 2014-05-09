//
//  AdfitYoutubeViewController.h
//  AdfitYoutube
//
//  Created by Kittipong Kulapruk on 5/8/2557 BE.
//  Copyright (c) 2557 Kittipong Kulapruk. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>
#import <MediaPlayer/MediaPlayer.h>
#import "HCYoutubeParser.h"

@protocol YoutubeDelegate <NSObject>
@required
- (void) closeYoutubeWithFinish:(BOOL) finish;
@end

@interface AdfitYoutubeViewController : UIViewController {
    UIActivityIndicatorView * _activityIndicator;
    NSString * _urlToLoad;
    MPMoviePlayerController * mp;
    NSTimer * mpTimer;
    float duration;
    UILabel * timerLabel;
}

@property (nonatomic) id <YoutubeDelegate> youtubeDelegate;
@property (nonatomic,strong) NSString * youtubeId;

@end
