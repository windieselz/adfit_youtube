//
//  AdfitYoutubeViewController.m
//  AdfitYoutube
//
//  Created by Kittipong Kulapruk on 5/8/2557 BE.
//  Copyright (c) 2557 Kittipong Kulapruk. All rights reserved.
//

#import "AdfitYoutubeViewController.h"

@interface AdfitYoutubeViewController ()

@end

@implementation AdfitYoutubeViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    [self.view setBackgroundColor:[UIColor blackColor]];
    _activityIndicator = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhiteLarge];
    [_activityIndicator setCenter:self.view.center];
    [_activityIndicator setHidesWhenStopped:YES];
    [self.view addSubview:_activityIndicator];
    [self loadYoutubeMetadata];

    UIButton * btClose = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    [btClose setFrame:CGRectMake(10, 20, 48, 32)];
    [btClose setTitle:@"Done" forState:UIControlStateNormal];
    [btClose addTarget:self action:@selector(closeMp:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btClose];

    
    CGRect timerFrame = CGRectMake(10, self.view.frame.size.height -30, self.view.frame.size.width , 30);
    timerLabel = [[UILabel alloc] initWithFrame:timerFrame];
    [timerLabel setBackgroundColor:[UIColor clearColor]];
    [timerLabel setTextColor:[UIColor whiteColor]];
    [self.view addSubview:timerLabel];
}

-(void) loadYoutubeMetadata{

    NSString * youtubeUrl = [NSString stringWithFormat:@"http://www.youtube.com/watch?v=%@",self.youtubeId];
    NSURL *url = [NSURL URLWithString:youtubeUrl];
    _activityIndicator.hidden = NO;
    [_activityIndicator startAnimating];
    [HCYoutubeParser thumbnailForYoutubeURL:url thumbnailSize:YouTubeThumbnailDefaultHighQuality completeBlock:^(UIImage *image, NSError *error) {
        if (!error) {
            NSDictionary *qualities = [HCYoutubeParser h264videosWithYoutubeURL:url];
            NSLog(@"quality : %@ \n",qualities);
            _urlToLoad = [NSURL URLWithString:[qualities objectForKey:@"medium"]];
            [self performSelectorOnMainThread:@selector(initVideo) withObject:self waitUntilDone:NO];
        }
        else {
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Error" message:[error localizedDescription] delegate:nil cancelButtonTitle:@"Dismiss" otherButtonTitles:nil];
            [alert show];
        }
    }];

}
- (NSUInteger)supportedInterfaceOrientations
{
    return UIInterfaceOrientationMaskPortrait;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void) initVideo{
    duration = 0.0f;
    //NSLog(@"youtube modal");
    mp = [[MPMoviePlayerController alloc] initWithContentURL:_urlToLoad];
    mp.controlStyle = MPMovieControlStyleNone;
    mp.fullscreen = NO;
    [mp prepareToPlay];

    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(playbackFinished:)
                                                 name:MPMoviePlayerPlaybackDidFinishNotification
                                               object:mp];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(MPMoviePlayerLoadStateDidChange:)
                                                 name:MPMoviePlayerLoadStateDidChangeNotification
                                               object:mp];
    
    mp.scalingMode = MPMovieScalingModeAspectFit;
    mp.movieSourceType    = MPMovieSourceTypeStreaming;

    // Add the moviePlayer's view as a subview of a my UIViewController's view.
    mp.view.frame = CGRectMake(0, 80, self.view.frame.size.width, 300);
    mp.view.center = self.view.center;
    mp.view.backgroundColor = [UIColor blackColor];
}

-(IBAction)handleTimer:(id)sender{
    if(duration > 0.0f){
        CGFloat currentPlayedTime = [mp currentPlaybackTime];
        //NSLog(@"Current time is %g seconds", currentPlayedTime);
        int timeLeft = ceil(duration - currentPlayedTime);
        [timerLabel setText:[NSString stringWithFormat:@"%i seconds left",timeLeft]];
    }
}

-(IBAction)closeMp:(id)sender{
    [self clearTimer];
    [self.youtubeDelegate closeYoutubeWithFinish:NO];
}

-(void) MPMoviePlayerLoadStateDidChange:(NSNotification *)notification{
    MPMoviePlayerController *player = notification.object;
    if (player.loadState && MPMovieLoadStatePlayable)
    {
        //NSLog(@"Movie is Ready to Play");
        if(![mpTimer isValid]){
        [_activityIndicator stopAnimating];
        [self.view addSubview:mp.view];
        duration = mp.duration;
        [mp play];
        mpTimer = [NSTimer scheduledTimerWithTimeInterval:1.0
                                                   target: self
                                                 selector: @selector(handleTimer:)
                                                 userInfo: nil
                                                  repeats: YES];
        }
    }
}

-(IBAction)playbackFinished:(id)sender {
    NSLog(@"Play Finish");
    [self clearTimer];
    [self.youtubeDelegate closeYoutubeWithFinish:YES];
}

-(void) clearTimer{
    if([mpTimer isValid]){
        [mpTimer invalidate];
        mpTimer = nil;
    }
}


@end
